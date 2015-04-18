
require("igraph");


plotGraph <- function(csvFile, plotTitle) {
    
  
  data <- read.csv(csvFile);
  net <- graph.data.frame(data, directed=TRUE);

  plot(
    net, 
    edge.arrow.size=0.3, 
    vertex.size=5, 
    vertex.label=NA, 
    layout=layout.auto,
    main=plotTitle
  );
  

}

pdf(file="graphs.pdf", width=8, height=8);

par(mai=c(0,0,0.3,0))
layout(matrix(1:4, nr=2, byrow=TRUE))

plotGraph("random_erdos_renyi.csv", "Random");
plotGraph("regular_lattice.csv", "Regular lattice");
plotGraph("random_watts_strogatz.csv", "Small-worlds");
plotGraph("random_barabasi_albert.csv", "Scale free");

dev.off();
