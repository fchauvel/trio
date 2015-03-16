
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

Trio is a java application, which should be run from the command line. The 
following example show its primary usage.

    Usage: trio [options] input.trio
    where 'options' are:
      -o, --observe=TAG  the tag of the components whose activity shall be observed
      -c, --control=TAG  the tag of the components whose activity shall be controlled
      -r, --runs=INTEGER the number of sample for statistical evidence
      -t, --trace=FILE   the file where the generated data shall be stored

Example: 

    trio -t result.csv --run=10000 system.trio

Below is an example of output of successful invocation

    $> java -jar trio-bundle.jar -o service -c infra -t trace.csv samples/sensapp_topo4.trio
    Trio v0.1 -- Topology Robustness IndicatOr
    Copyright (C) 2014 - SINTEF ICT
    Licensed under LGPLv3

    SYSTEM: SensApp, topology no. 4
    SCENARIO: Robustness of the 'service' layer to failure in the 'infra' layer
    INDICATORS:
     + Robustness: 0.0297
     + Five most sensitive components:
       1: 1.7375e+00  VM2
       2: 8.2376e-01  VM1
       3: 6.8183e-01  VM4
       4: 6.6244e-01  VM3
       5: 5.6873e-01  VM5
     + Five most harmful sequences:
       1: 1.9200e-01 [none,  VM2]
       2: 5.6990e-02 [none,  VM3,  VM2]
       3: 5.2990e-02 [none,  VM4,  VM2]
       4: 5.1991e-02 [none,  VM1,  VM2]
       5: 3.1994e-02 [none,  VM5,  VM2]

    That's all folks!


### Describing the topology of the system

The topology under study shall be described in a separate text file, as shown in the
example below, where we described the deployment of the SensApp system. SensApp is made
of five REST services (i.e., Admin, Notifier, Registry, Dispatcher and Storage), 
that must be run on the top of servlet containers (SCX), themselves requiring 
Java Runtime Environment (JRE) to run properly. The topology thus includes all 
these services and the related platform as well as the underlying infrastructure 
(physical or virtual machine) on which these software component are deployed.

    #
    # The SensApp services and their dependencies
    #

    system: 
        'SensApp, topology no. 1'

    components:
        - Admin         requires    Notifier and Registry and Storage and SC1
        - Notifier      requires    SC2
        - Registry      requires    Storage and SC2
        - Dispatcher    requires    Notifier and Registry and Storage and SC2
        - Storage       requires    DB and VM2
        - DB            requires    VM2
        - SC1           requires    JRE1
        - SC2           requires    JRE2
        - JRE1          requires    VM1
        - JRE2          requires    VM2
        - VM1
        - VM2

    tags:
        - 'infra'     on  VM1, VM2
        - 'platform'  on  DB, SC1, SC2, JRE1, JRE2
        - 'service'   on  Admin, Registry, Storage, Dispatcher, Notifier


The description of the system is made of three sections: "system", "components" 
and "tags". 

 - The "system" section associates a name to the system;
 - the "components" section captures the topology as an assembly of components 
(and their dependencies);
 - the "tags" section let the user partitions the topology by arbitrarily 
classifying components.

Technical readers may found a complete description of the language used to 
capture topologies in the associated ANTLR grammar, available at: 
https://github.com/fchauvel/trio/blob/master/src/main/antlr4/eu/diversify/trio/builder/Trio.g4


#### Expressing dependencies between components

The most important section is the one entitled "components". It describes the 
components (in the broadest meaning of the term) and their dependencies. Dependencies are 
specified by the (optional) "requires" clause, which is followed by a logical condition
over the other components. For instance, in the above example, the expression:

    - Registry      requires    Storage and SC2

states that the "registry" service of SensApp will remain operational as long as
the Storage component and the underlying Servlet container (i.e., SC2) are 
operational as well. These logical expressions will permit to propagate failures and,
in turn, to evaluate the robustness of the system.

#### Partitioning the topology

In many cases one may not be interested in the overall robustness, but in the 
robustness of a subpart of the system with respect to failure of another. Trio let you 
partition the topology by tagging the components in the "tag" section. For instance,
in the above example, the line:

    - 'infra'     on  VM1, VM2

states that both components "VM1" and "VM2" are tagged as "infra". Along with 
other tagging clauses, they will permit to evaluate the robustness of the 
infrastructure (so called "infra"), with respect to others partitions 
(e.g., platform or services).
