#
# Generate random graphs and compute some topological metrics on them
# Copyright (C) 2015, SINTEF ICT
#
# Franck CHAUVEL
#

require("igraph");

args <- commandArgs(trailingOnly = TRUE);


# Read the property file containing the configuration
setup <- read.table(
  "setup.properties", 
  sep="=", 
  row.names=1, 
  strip.white=TRUE
);


kinds <- c("random", "scale free", "small world");

#
# Compute the kind of a graph based on its id
#
kind.of <- function(graphId) {
  return((graphId %% 3) + 1); 
}


#
# Generate a given number of graph on disk
#
generate.one.graph <- function(index) {
    
  minSize <- as.numeric(as.character(setup["min.assembly.size", ]));
  maxSize <- as.numeric(as.character(setup["max.assembly.size", ]));
  
  size <- runif(1, minSize, maxSize);
  kind <- kind.of(index);
  
  if (kind == 1) {
    graph <- erdos.renyi.game(
      n = size, 
      p = runif(1, 0, 1),
      directed = TRUE
    );
    
  } else if (kind == 2) {
    graph <- barabasi.game(
      n = size, 
      power = runif(1, 0, 1), 
      directed = TRUE
    );
    
  } else {
    graph <- watts.strogatz.game(
      dim = 1,
      size = size,
      nei = runif(1, 0, 0.1) * size, 
      p = runif(1, 0, 1)
    );
  }
  
  return(graph);
  
}


metrics <- c(
  "vertex count",
  "edge count",
  "density",
  "average distance",
  "diameter",
  "average node degree",
  "clustering coefficient", 
  "assortativity", 
  "centrality degree", 
  "centrality betweenness", 
  "centrality eigenvalue", 
  "centrality closeness"
);



#
# Generate a given number of random graphs, save them on disk and 
# return a table describing their key properties
#
generate.graphs <- function(firstId, lastId) {
  
  n <- lastId - firstId + 1;
  
  data <- matrix(NA, n, length(metrics) + 2); # room for id & kind
  colnames(data) <- append(c("id", "kind"), metrics);
     
  for(index in firstId:lastId) {
    print(index);
    each.graph <- generate.one.graph(index);
    
    data[index, 1] <- index;
    data[index, 2] <- kinds[kind.of(index)];
    data[index, seq(3, length(metrics) + 2)] <- analyse.graph(each.graph);
    
    save.as.csv(each.graph, index)
  }
  
  
  result <- data.frame(data);
  write.csv(result, "graphs.csv", row.names = FALSE);
  
  return(result);
}




#
# Store a given graph as a CSV file
#
save.as.csv <- function(aGraph, index) {
  pattern <- sprintf("%s/%s", as.character(setup["graph.directory",]), as.character(setup["graph.file.pattern", ]));
  fileName <- sprintf(pattern, index);
  data <- data.frame(get.edgelist(aGraph));
  write.csv(data, fileName, row.names=FALSE);
}


#
# Compute the correlation table with respect to a reference variable
#
correlations <- function(data, reference, properties)  {
  
  table <- matrix(NA, length(properties), length(levels(data$kind)) + 1);
  colnames(table) <- append(c("total"), levels(data$kind));
  rownames(table) <- properties;
  
  for (p in properties) {
    table[p, "total"] <- cor(data[, reference], data[, p]);
    for (k in levels(data$kind)) {
      d <- subset(data, kind == k);  
      table[p, k] <- cor(d[,reference], d[, p]);
    }
  }
  
  return(table);
}


graphStatistics <- function() {
  files <- allGraphFiles();
  
  data <- matrix(NA, length(files), length(metrics) + 1);
  colnames(data) <- append(c("id"), metrics);
  
  for (eachFile in files) {
    graphId <- graphId(eachFile);
    graph <- graphFromFile(eachFile);
    data[graphId, ] <- append(c(graphId), analyseGraph(graph)); 
  }
  
  return(data);
}


#
# Calculate various properties of interest on a given graph
#
analyse.graph <- function(eachGraph) {
  return(c(
    vcount(eachGraph),
    ecount(eachGraph),
    graph.density(eachGraph),
    average.path.length(eachGraph),
    diameter(eachGraph),
    mean(degree.distribution(eachGraph, mode="all")),
    transitivity(eachGraph, type="global"),
    assortativity.degree(eachGraph),
    centralization.degree(eachGraph)$centralization,
    centralization.betweenness(eachGraph)$centralization,
    centralization.evcent(eachGraph)$centralization,
    centralization.closeness(eachGraph)$centralization
  ));
}

#
# Analyse a collection of graphs
#
analyseAllGraph <- function(graphs) {
  
  data <- matrix(NA, length(graphs), length(metrics));
  colnames(data) <- metrics;
  
  for (i in seq(1, length(graphs))) {
    eachGraph <- graphs[[i]];
    
    data[i, ] <- analyse.graph(eachGraph);
  }
  
  return(data);
}



allGraphFiles <- function() {
  return(list.files(path="graphs", pattern="graph_(\\d+).csv"));
}


graphId <- function(graphName) {
  groups <- regmatches(graphName, regexec("graph_(\\d+).csv", graphName));
  return(as.numeric(groups[[1]][2]));
}

graphFromFile <- function(fileName) {
  edges <- read.csv(sprintf("graphs/%s", fileName));
  graph <- graph.data.frame(edges);
  return(graph);
}

fileNameOfGraph <- function(graphId) {
  return(sprintf("graph_%d.csv", graphId));
}

graphWithId <- function(graphId) {
  return(graphFromFile(fileNameOfGraph(graphId)));
}

viewGraph <- function(graphId) {
  plot(
    graphWithId(graphId),
    vertex.label=NA,
    vertex.size = 5,
    edge.arrow.size=0.3
    );
}

viewCorrelation <- function(data, reference, property) {
  n <- nlevels(data$kind);
  plot(
    data[, reference] ~ data[, property], 
    xlab = property,
    ylab = reference,
    col= seq(2, n+1)[data$kind], 
    pch=c(1,3,4)[data$kind], 
    las=1
  );
  legend(
    "topleft", 
    legend = levels(data$kind),
    pch = c(1,3,4),
    col = seq(2, n+1),
    bty = "n"
  );
}

viewBenchmark <- function(data) {
  
  op <- par(mar = c(5,5,4,2));
  
  plot(
    (data$duration / 10e6) ~ data$vertex.count,
    col = c(2, 3, 4)[data$kind],
    pch = c(1, 3, 4)[data$kind],
    ylab = "Duration (ms)",
    xlab = "Architecture size (component count)",
    las=0
    );
  
  legend(
    "topleft", 
    legend = kinds, 
    col = c(2, 3, 4),
    pch = c(1, 3, 4),
    bty="n"
    );
  
  
}


load.all.data <- function() {
  graphs <- read.csv("graphs.csv");
  perfs <- read.csv("scalability.csv");
  data <- merge(graphs, perfs, by="id");
  
  return(data);
}


#
# Main program: Generation of graphs according to what is described in 'setup.properties'
#

if (args[1] == "generate") {
  dir <- as.character(setup["graph.directory", ]);
  dir.create(dir, showWarnings = FALSE);
  
  firstId <- as.numeric(as.character(setup["first.graph.id",]));
  lastId <- as.numeric(as.character(setup["last.graph.id",]));
  
  generate.graphs(firstId, lastId);

} else if (args[1] == "plot") {
  data <- load.all.data();
  
  pdf("benchmark.pdf", width=10, height=8);
  viewBenchmark(data);
  dev.off();
  
} else if (args[1] == "regression") {
  data <- load.all.data();
  model <- lm(data$duration ~ data$vertex.count + data$kind + data$density);
  sink("regression.txt", stdout());
  print(summary(model));
  sink();
  
}
