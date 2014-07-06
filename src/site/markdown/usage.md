
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

The robustness analyser is a standalone application. It takes three main parameters: 

 - *Analysis Level* is the level at which the robustness is
    computed. It can be either 'type' or 'instance'. When type is
    selected, only the relationship between CloudML types will be
    considered, whereas

    $> java -jar robustness-final.jar type my-deployment.json 1000