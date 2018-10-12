rm *.class

rm ./data/*.class
rm ./data/robot/*.class
rm ./io/*.class

#javac TestCreationDonnees.java

#java TestCreationDonnees ../cartes/carteSujet.map

javac TestSimulateur.java

java TestSimulateur
