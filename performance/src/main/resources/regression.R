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

plot(data);

dev.off();

#
# Linear regression analysis of the data
#
attach(data);

model <- lm(sqrt(duration/1000) ~ size + density); 
show(summary(model)); 

pdf("regression.pdf", width=8, height=6);
 
plot(duration/1000 ~ size, 
     col="blue", 
     pch=4,
     cex=0.8,
     las=1, 
     ylab="Duration (�s)", 
     xlab="Model size (# components)");

points(predict(model)^2 ~ size, 
       pch=20, 
       col="red");

legend("topleft", 
       legend=c("sampled durations", "predictions from model"),
       col=c("blue", "red"), 
       pch=c(4, 19),
       bty="n");

dev.off();  

detach(data); 

