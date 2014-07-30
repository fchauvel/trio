#
# Plot the data collected during the scalability test
#

data <- read.csv("scalability.csv");

par(mar=c(5,5,1,1));

attach(data);

fit <- lm(duration ~ poly(size, degree=2, raw=TRUE));
#fit <- lm(duration ~ size);
show(summary(fit));

myBlue <- rgb(0,0,255,50, maxColorValue=255);

plot( duration~size, 
        log="xy", 
        xlab="number of components", 
        ylab="simulation duration (ms)",
        pch=16,
        col=myBlue,
        las=1);

grid();

points( size, 
        predict(fit), 
        col="red", 
        pch=18);

legend( "topleft", 
        c("collected data", "model (second order polynomial)"), 
        pch=c(16, 18), 
        col=c(myBlue, "red"), 
        bty="n");

dev.copy(pdf, height=6, width=9, "scalability.pdf");
dev.off();

p <- sprintf("%2.2e x^2 + %2.2e x + %2.2e", fit$coefficients[3], fit$coefficients[2], fit$coefficients[1]);
show(p);

dev.new();
par(mar=c(5,5,1,1));
hist(   data$complexity, 
        breaks=quantile(data$complexity, seq(0,1,0.1)),
        xlab="Average number of boolean operators in propagation model",
        las=1, 
        col="lightgrey",
        main=NULL);
box(lty=1);

dev.copy(pdf, height=6, width=9, "complexity.pdf");
dev.off();