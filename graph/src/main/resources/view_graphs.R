



plotGraph <- function(csvFile) {
    
  pdfFile <- sub(".csv", ".pdf", csvFile);
  pdf(file=pdfFile, width=6, height=6);
  
  data <- read.csv(csvFile);
  net <- graph.data.frame(data, directed=TRUE);

  plot(
    net, 
    edge.arrow.size=0.3, 
    vertex.size=5, 
    vertex.label=NA, 
    layout=layout.auto
  );
  
  dev.off();

}


plotGraph("random_barabasi_albert.csv");
plotGraph("random_erdos_renyi.csv");
plotGraph("random_watts_strogatz.csv");