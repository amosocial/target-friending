The main class used for the experiments is KDD 18.

Data
The data folder includes the datasets used for experiments. Each line of the data file shows one edge.
----------------------------------------
            number of nodes
wiki        8300
hepth       27770
youtube     1157900
------------------------------


How to use KDD 18?
The experimental results can be reproduced by simply using kdd18.jar.

arguments: <path of the data><number of the nodes><index of the initiator><index of the target node>

An example on wiki for initiator 1565 and target 28:
     java -Xmx2048m -jar kdd18.jar data/wiki.txt 8300 2565 28
     
Note that the results for the same input may not be exactly the same because the algorithm is randomized. However, they should be 
very close to each other.


Dependency
The code of the algorithm is mainly in RBA.java
The whole project depends on influence.jar which is the basic class for the IC and LT graphs.


Please contact Amo (gxt140030 at utdallas.edu) for any concerns.
