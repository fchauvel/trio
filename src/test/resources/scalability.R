#
# Plot the data collected during the scalability test
#


#
# Build the prediction model on the given dataset
#
model <- function(dataset) {
    attach(dataset);
    
    fit <- lm(duration ~ poly(size, degree=2));
    
    show(summary(fit));

    detach(dataset);
 
    return(fit);
}


#
# Apply the given model on the given dataset and show the result
#
show_fitness <- function(dataset, model, pdfFile) {
    attach(dataset);

    dev.new();
    par(mar=c(5,5,1,1));

    plot( duration~size, 
            log="xy", 
            xlim=c(1, max(size)),
            xlab="number of components", 
            ylab="duration of 1 failure sequence simulation (ms)",
            pch=1,
            col="blue",
            las=1);

    grid();

    points( size, 
            predict(model, dataset), 
            col="red", 
            pch=18);

    detach(dataset);

    legend( "topleft", 
            c("collected data", "model (second order polynomial)"), 
            pch=c(1, 18), 
            col=c("blue", "red"), 
            bty="n");



    dev.copy(pdf, height=6, width=9, pdfFile);
    dev.off();

} 

data <- read.csv("scalability.csv");

ndata <- nrow(data);
cut <- ndata / 2;

training <- data[1:cut,];
test <- data[cut:ndata,];

prediction <- model(training);

show_fitness(training, prediction, "training.pdf");

show_fitness(test, prediction, "test.pdf");


#p <- sprintf("%2.2e x^2 + %2.2e x + %2.2e", fit$coefficients[3], fit$coefficients[2], fit$coefficients[1]);
#show(p);

#dev.new();
#par(mar=c(5,5,1,1));
#hist(   data$complexity, 
#        breaks=quantile(data$complexity, seq(0,1,0.1)),
#        xlab="Average number of boolean operators in propagation model",
#        las=1, 
#        col="lightgrey",
#        main=NULL);
#box(lty=1);

#dev.copy(pdf, height=6, width=9, "complexity.pdf");
#dev.off();
