# 
# Regression analysis of the data produced by the TRIO benchmark
#
# Run it, with the command:
# $> R CMD BATCH "--args your_data.csv" regression.R
#

options(echo=TRUE);

data_file = "scalability.csv"

#
# Fetch the parameters provided to the script
#
args=commandArgs(trailingOnly = TRUE);
if (length(args) > 0) {
   data_file = args[1];
}

#
# Summarize and plot the raw data
#
data <- read.csv(file=data_file);

show(summary(data));
 
pdf("raw_data.pdf", width=8, height=6);

panel.hist <- function(x, ...)
{
  usr <- par("usr"); on.exit(par(usr))
  par(usr = c(usr[1:2], 0, 1.5) )
  h <- hist(x, plot = FALSE)
  breaks <- h$breaks; nB <- length(breaks)
  y <- h$counts; y <- y/max(y)
  rect(breaks[-nB], 0, breaks[-1], y, col = "cyan", ...)
}

panel.cor <- function(x, y, ...) {
  horizontal <- (par("usr")[1] + par("usr")[2]) / 2;
  vertical <- (par("usr")[3] + par("usr")[4]) / 2;
  text(horizontal, vertical, substitute(rho == V, list(V = format(cor(x,y), digits=3))), cex=2)
}

pairs(
  data[,-1],
  diag.panel = panel.hist,
  upper.panel = panel.cor,
  pch=4,
  cex=0.8
);

dev.off();

#
# Linear regression analysis of the data
#
attach(data);

toMS <- function(d){ return (d/10e6); }

model <- lm(sqrt(toMS(duration)) ~ size + density); 
show(summary(model)); 

pdf("regression.pdf", width=8, height=6);
 
plot(toMS(duration) ~ size, 
     col="blue", 
     pch=4,
     cex=0.8,
     las=1, 
     ylab="Duration (ms)", 
     xlab="Model size (# components)");

points(predict(model)^2 ~ size, 
       pch=21, 
       col="red",
       cex=0.8);

legend("topleft", 
       legend=c("sampled durations", expression(paste("Predictions using ", sqrt(duration) == B[2] %.% size + B[1] %.% density + B[0] + epsilon))),
       col=c("blue", "red"), 
       pch=c(4, 21),
       bty="n");

dev.off();  

detach(data); 

