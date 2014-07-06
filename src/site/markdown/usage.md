
# Robustness Analyser

The robutness analyzer computer robustness indicators on graphs. 

 - **Overall Robustness**: is a percentage that reflect to which extent
the given CloudML deployment is robust. A overall robustness of 100 %
means that one shall separately destroy each single part for the
system to be completely destroyed

 - **Most sensitive components**: the list of components order by the
   amount of system that depends on them.

 - **Most harmful sequences** of attacks is the sequences of attack to
   particular components that takes a large part of the system down.


## Usage

The robustness analyser is a standalone application. It takes two main parameters: 

 - The **topology** to analyse, is the path to file containing a description of 
the topology of interest

 - The **number** of extinction sequence to run. This parameter directly impact 
the strength of the statistical evidence associated with the output indicators.

The following command illustrates how to run TRIO from the command line

    $> java -jar trio-final.jar my-deployment.json 1000