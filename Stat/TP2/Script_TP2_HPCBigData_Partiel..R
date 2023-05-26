# Script TP2 HPC-BigData - Modele linaire gaussien


######################
#Partie 1 :
######################

# 1 - Chargement librairies et donnees
library(MASS)
data=read.table(file="DataTP.txt",header=TRUE)

names(data)
summary(data)
dim(data)
#Assurez-vous que R considere bien par defaut les variables JJ et STATION comme etant de type factor 
#(effectifs par modalite affiches dans le summary), sinon forcez le type comme ceci : 
#data$JJ=as.factor(data$JJ)
#data$STATION=as.factor(data$STATION)

# 2 - Etude preliminaire et regression simple

aix=subset(data,STATION=="Aix")
summary(aix)
sd(aix$O3o)
sd(aix$O3p)

par(mfrow=c(1,2))
hist(aix$O3o,breaks=seq(0,300,30),ylim=c(0,100))
hist(aix$O3p,breaks=seq(0,300,30),ylim=c(0,100))
# on impose ici les memes classes pour pouvoir comparer les histogrammes

x11()
boxplot(aix$O3o,aix$O3p,names=c("[O3]obs","[O3]prevu"))
# Les previsions semblent surestimer globalement les mesures

var.test(aix$O3o,aix$O3p)
# -> les variances peuvent etre considerees comme egales
t.test(aix$O3o,aix$O3p,var.equal=T)
# -> les moyennes sont significativement differentes : les previsions 03p sont biaisees (biais positif)

cor(aix$O3o,aix$O3p)
cor.test(aix$O3o,aix$O3p)
#liaison lin�aire significative detectee

plot(aix$O3p,aix$O3o)
regsimple=lm(O3o~O3p,data=aix)
summary(regsimple)
# -> les 2 parametres du modele sont juges significativement differents de 0
# -> la regression simple n'explique que 22% de la variance totale du predictand
# en considerant d'autres predicteurs, le modele statistique peut certainement etre ameliore

x11()
plot(aix$O3p,aix$O3o,xlab="O3 PREVU MOCAGE",ylab="O3 OBSERVE",
     main="PREVISIONS 24H",pch='+')
abline(regsimple,col="red")

x11()
plot(aix$O3o,type ="l",lwd=2,main="Concentration d'ozone � Aix",xlab="Date",ylab="[O3]")
points(aix$O3p,col="blue",pch="+")
points(fitted(regsimple),col="red",pch="+")
legend(0,268,lty=1,col=c("black"),legend=c("observ�e"),bty="n")
legend(0,256,pch="+",col=c("blue","red"),legend=c("       prevue","       ASsimple"),bty="n") 
# -> le traitement statistique tire les previsions brutes vers la moyenne du predictand mais est incapable d'atteindre les valeurs extremes
# la variabilit� du predictand est mal reproduite, il faut enrichir la regression pour obtenir une flexibilite plus forte du modele.


# 3 - Regression multiple : predicteurs continus


x11()
par(mfrow=c(3,2))
hist(data$O3p)
hist(data$TEMPE)
hist(data$FF)
hist(data$RMH2O)
hist(data$NO2)
hist(log(data$NO2))
cor(data$O3o,data$NO2)
cor(data$O3o,log(data$NO2))
cor.test(data$O3o,log(data$NO2))
# -> la distribution de NO2 est tres dissymetrique, l'emploi de la fonction log permet d'obtenir un profil mieux correle au predictand.
# -> on utilisera donc plutot la variable log(NO2)comme pr�dicteur par la suite.

anova1 = lm(O3o~JJ,data)
model.matrix(anova1)
#analyser la sortie de la fonction "summary" et de la "design matrix" , quelle contraints d'identification R a-t-il imposée par default ?
# -> R a impose une contrainte d'identification sur le parametre intercepte, qui est egale a la moyenne des observations du predictand
# -> cette contrainte est necessaire pour que le modele soit identifiable
# -> on peut la supprimer en ajoutant l'option "-1" dans la formule de la regression
anovax = lm(O3o~JJ-1,data)
summary(anovax)
# -> les 7 parametres du modele sont juges significativement differents de 0
# -> la regression multiple n'explique que 30% de la variance totale du predictand
# -> les parametres du modele sont tous significativement differents de 0
# -> les parametres du modele sont tous significativement differents les uns des autres
#anova2 = lm(O3o~C(JJ,sum),data)
#summary(anova2)

#ANCOVA : modèle complet et sélection automatique des prédicteurs

regcomplet = lm(O3o~O3p+TEMPE+RMH2O+log(NO2)+FF+STATION+JJ,data)
summary(regcomplet)
# les predicteurs les plus pertinants sont ceux qui ont une p_value trop petites ( avec bcp d'asterix ***)

#AIC criteria
regaic = stepAIC(regcomplet)
#BIC criteria 
regbic = stepAIC(regcomplet,k=log(nrow(data)))
regbicint = stepAIC(lm(O3o~.*.,data),k=log(nrow(data)))

#Comparer sur un meme graphe les observations d'ozone O3o, les previsions brutes de mocage o3p et les previsions obtenues apres post traitements statistiques(modèle BIC avec interactions et régression simple exploitant le prédicteur O3p.) et Commenter.
x11()
plot(data$O3o,type ="l",lwd=2,main="Concentration d'ozone � Aix",xlab="Date",ylab="[O3]")
points(data$O3p,col="blue",pch="+")
points(fitted(regbicint),col="red",pch="+")
lines(fitted(regbicint),col="red",lwd=3)
lines(fitted(regaic),col="blue",lwd=3)
lines(fitted(regcomplet),col="green",lwd=3)
legend("topleft",legend=c("Observations","Brutes","AIC","BIC","AIC+BIC","Modèle complet"),col=c("black","blue","red","green","red","blue"),lty=c(1,1,1,1,1,1),lwd=c(1,1,1,1,1,1,1
),pch=c(NA,1,1,1,1,1),bty="n")
# -> On observe que la plupart des observations d'ozone sont bien corrélées avec la plupart des observations de mocage o3p.
# -> Les prévisions obtenues après post traitements statistiques (modèle BIC avec interactions et régression simple exploitant le prédicteur O3p.) sont plus proches des observations que les prévisions brutes de mocage o3p.
# -> Le modèle complet est le plus proche des observations.
# -> Le modèle AIC est le plus éloigné des observations.
# -> Le modèle BIC est le plus proche des observations.
# -> Le modèle AIC+BIC est le plus proche des observations.
# -> Le modèle complet est le plus proche des observations.
