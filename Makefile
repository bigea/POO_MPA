# Ensimag 2A POO - TP 2018/19
# ============================
#
# Ce Makefile permet de compiler le test de l'ihm en ligne de commande.
# Alternative (recommandee?): utiliser un IDE (eclipse, netbeans, ...)
# Le but est ici d'illustrer les notions de "classpath", a vous de l'adapter
# a votre projet.
#
# Organisation:
#  1) Les sources (*.java) se trouvent dans le repertoire src
#     Les classes d'un package toto sont dans src/toto
#     Les classes du package par defaut sont dans src
#
#  2) Les bytecodes (*.class) se trouvent dans le repertoire bin
#     La hierarchie des sources (par package) est conservee.
#     L'archive bin/gui.jar contient les classes de l'interface graphique
#
# Compilation:
#  Options de javac:
#   -d : repertoire dans lequel sont places les .class compiles
#   -classpath : repertoire dans lequel sont cherches les .class deja compiles
#   -sourcepath : repertoire dans lequel sont cherches les .java (dependances)

all: testInvader testLecture

testInvader:
	javac -d bin -classpath bin/gui.jar -sourcepath src src/TestInvader.java

simulateur:
	javac -d bin -classpath bin/gui.jar -sourcepath src src/gui2/Simulateur.java

testSimulateur:
	javac -d bin -classpath bin/gui.jar -sourcepath src src/TestSimulateur.java

testLecture:
	javac -d bin -sourcepath src src/TestLecteurDonnees.java

testCreationDonnees:
	javac -d bin -classpath bin/gui.jar -sourcepath src src/TestCreationDonnees.java

testDeplacement:
	javac -d bin -classpath bin/gui.jar -sourcepath src src/TestDeplacement.java

testStrategieElementaire:
	javac -d bin -classpath bin/gui.jar -sourcepath src src/TestStrategieElementaire.java

testStrategieAvancee:
	javac -d bin -classpath bin/gui.jar -sourcepath src src/TestStrategieAvancee.java

# Execution:
# on peut taper directement la ligne de commande :
#   > java -classpath bin:bin/gui.jar TestInvader
# ou bien lancer l'execution en passant par ce Makefile:
#   > make exeInvader
exeInvader:
	make testInvader
	java -classpath bin:bin/gui.jar TestInvader

exeSimulateur:
	make testSimulateur
	java -classpath bin:bin/gui.jar TestSimulateur cartes/carteSujet.map

exeLecture:
	make testLecture
	java -classpath bin TestLecteurDonnees cartes/carteSujet.map

exeCreation:
	make testCreationDonnees
	java -classpath bin:bin/gui.jar TestCreationDonnees cartes/carteSujet.map

exeDeplacement:
	make testDeplacement
	java -classpath bin:bin/gui.jar TestDeplacement cartes/carteSujet.map

exeStrategieElementaire:
	make testStrategieElementaire
	java -classpath bin:bin/gui.jar TestStrategieElementaire cartes/carteSujet.map

exeStrategieAvancee:
	make testStrategieAvancee
	java -classpath bin:bin/gui.jar TestStrategieAvancee cartes/desertOfDeath-20x20.map

clean:
	find . -name "*.class" -exec rm {}  \;
