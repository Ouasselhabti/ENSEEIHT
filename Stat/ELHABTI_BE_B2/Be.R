# NOM : EL HABTI
# PRENOM : OUASSEL
# GROUPE : B2

# Importer la bibliothèque MASS
library(MASS)

data = read.table("Data.txt",header=TRUE)

#regression simple de O3 par O3v : en exploitant que la variable O3v comme predicteur
lm.out1=lm(O3~O3v,data=data)

plot(data$O3,data$O3v)
# tracage de nuage de pts correspondant en y superposant la droite de regression
x11()
plot(data$O3v,data$O3,xlab="O3 hier",ylab="O3 OBSERVE",
     main="PREVISIONS 24H",pch='+')
abline(lm.out1,col="red")

#analyser les perdormances de ce modèle
summary(lm.out1)
# en utilisant ce modèle, on observe :

# - Intercept : L'estimation de la constante est de 28.50249, avec une erreur standard de 6.57153.
    # Ce coefficient représente la valeur attendue de la variable dépendante lorsque O3v==0
#- O3v : L'estimation du coefficient de O3v==0.68235, et erreur==0.06929. 
    #Ce qui indique dans notre cas que, pour une unité d'augmentation de la variable O3v, la variable dépendante O3 augmente en moyenne de 0.68235 unités.

#- La constante et le coefficient pour O3v sont  statistiquement significatifs avec des p-valeurs très faibles (p < 0.001).
    # Ce qui nous mène a supposer qu'il y a une relation significative entre les variables O3 et O3v dans le modèle.

#  R^2 = 0,4686, cad  que le modèle explique 46.86 % de la variance totale de la variable dépendante. 

# Et pour conclure regardons le F test : 
 #- Dans notre cas, l'hypothèse nulle est que tous les coefficients de régression sont nuls, rejeté vu qu'ont une p-valeur très faible (p < 2.2e-16). Et donc : 

 ###### le modèle global est statistiquement significatif.

 # 3 Modeles sans iteraction 

 # L'analyse du comportement de la variable O3 vis a vis de la variable RR : 
 # Pour cela on doit voit les deux sous échantillons où RR=sec ou RR = Pluie
# Modele lm.out2 exploitant les 6 prédicteurs disponibles
lm.out2=lm(O3~O3v+T+N+FF+DD+RR,data=data)

#analyse de ce modèle : 
summary(lm.out2)

# l'impact de la variable N sur le predictand : 
    # déja , on peut voir que la nébulosité est tres significatif dans le modele (***)
    # si on etudie O3 par rapport à N , on a Multiple R-squared:  0.3865, et dont elle explique seul ~39% du modele lineaire (de la variance total)

# Resultat relatifs au facteurs DD
    # On peut voir que les seuls directions du vent qui joue un role statistiquement significatif dans la concentration d'ozone c'est : le nord et le sud .
    #(Interesant il faut reflechir pourquoi l'est et l'ouest n'ont pas un role significatif (sous le cadre du modèle linéaire ))
    # peut etre  car les vents du nord et sub apportent de l'air frais ou moins pollué à la région étudiée ? 
#
# vu les p_values donné par R , les predicteur qui doivent etre conservés sont seut d'une p_value < 0.05 , avec des asterixs , dans notre cas on conserve : 
    #  !!!(O3v) , !(T) , !!!(N) , !(DDNord) , !(DDSud)



#evolution 
#AIC criteria
regaic = stepAIC(lm.out2)
#BIC criteria 
regbic = stepAIC(lm.out2,k=log(nrow(data)))
regbicint = stepAIC(lm(O3~.*.,data),k=log(nrow(data)))
x11()
plot(data$O3,type ="l",lwd=2,main="Concentration d'ozone",xlab="Date",ylab="[O3]")
points(data$O3v,col="blue",pch="+")
points(fitted(regbicint),col="red",pch="+")
lines(fitted(regbicint),col="red",lwd=3)
lines(fitted(regaic),col="blue",lwd=3)
lines(fitted(lm.out2),col="green",lwd=3)
legend("topleft",legend=c("Observations","Brutes","AIC","BIC","AIC+BIC","Modèle complet"),col=c("black","blue","red","green","red","blue"),lty=c(1,1,1,1,1,1),lwd=c(1,1,1,1,1,1,1
),pch=c(NA,1,1,1,1,1),bty="n")

# j'ai l'impression que le AIC est celui qui colle mieux (plus que le BIC+AIC)
#Parcontre , il ya des pics (valeurs extremes) qui sont inexpliquable par le modèle linéaire



# Modele lm.outBIC issu d'une sélection automatique qui exploite l'indice BIC


# Créer le modèle initial en utilisant la fonction lm()
lm.out <- lm(O3 ~ O3v + T + N + FF + DD + RR, data = data)

# Appliquer la sélection automatique avec stepAIC() en utilisant l'indice BIC
lm.outBIC <- stepAIC(lm.out, direction = "both", k = log(length(data$O3)), trace = FALSE)

# Afficher le résumé du modèle sélectionné
summary(lm.outBIC)

#BICINT d'ordre 2


lm.outBICint <- stepAIC(lm.out, direction = "both", k = log(length(data$O3)), trace = FALSE, scope = list(upper = ~ .^2))

# Afficher le résumé du modèle sélectionné
summary(lm.outBICint)

# 5 Visualisation 
x11()
plot(data$O3, type = "l", lwd = 2, main = "Concentration d'ozone", xlab = "Date", ylab = "[O3]")

points(predict(lm.out1), col = "red", pch = "+")

points(predict(lm.outBICint), col = "blue", pch = "+")

legend("topleft", legend = c("Observations", "lm.out1", "lm.outBICint"), col = c("black", "red", "blue"), pch = c(NA, "+", "+"), lty = c(1, 1, 1), lwd = c(2, 1, 1), bty = "n")

#En observant le graphique, on peut constater que les estimations du modèle lm.out1  semblent suivre globalement la tendance des observations réelles de la variable O3.
# Par contre il ya de la difference et nottament dans les pics, qui est inexpliqué
#D'autre part,  lm.outBICint  semblent mieux correspondre aux observations réelles. la raison :
# la méthode de sélection automatique btient compte de davantage de prédicteurs et donc arrive à capturer plus finement les variations de la variable O3 au cours du temps.



#6 evaluations des modèles :

#evaluation des modeles lm.out1 , lm.outBic et lm.outBicint en termes de biais et rmse

# Prédictions1
predictions1 <- predict(lm.out1, newdata = data)
bias1 <- mean(data$O3 - predictions1)
rmse1 <- sqrt(mean((data$O3 - predictions1)^2))
# Prédictions pour lm.outBIC
predictions_BIC <- predict(lm.outBIC, newdata = data)
bias_BIC <- mean(data$O3 - predictions_BIC)
rmse_BIC <- sqrt(mean((data$O3 - predictions_BIC)^2))

# Prédictions pour lm.outBICint
predictions_BICint <- predict(lm.outBICint, newdata = data)
bias_BICint <- mean(data$O3 - predictions_BICint)
rmse_BICint <- sqrt(mean((data$O3 - predictions_BICint)^2))

# Prédictions basées sur la stratégie de persistance
predictions_persistance <- lag(data$O3v)

# Biais et RMSE pour la stratégie de persistance
bias_persistance <- mean(data$O3 - predictions_persistance)
rmse_persistance <- sqrt(mean((data$O3 - predictions_persistance)^2))


# out1 : Il capture partiellement la relation entre la variable prédictrice O3v et la variable cible O3.
#outBIC : Il inclut plusieurs variables explicatives (O3v, T, N, DD) et on voit qu'il fournit des prédictions plus précises que lm.out1.
#outBICint : Il inclut les mêmes variables que lm.outBIC, plus l'interaction entre les variables T et FF. et donc : 
#offre une amélioration légèrement supérieure en termes de précision des prévisions par rapport à lm.outBIC.

#Plus performant ? 
#il faut d'abbord calculer le rmse et biais de la strategie trivial et comparer
yesterday_O3v <- lag(data$O3v, 1)  
predictions_persistence <- yesterday_O3v
bias_persistence <- mean(data$O3 - predictions_persistence)
rmse_persistence <- sqrt(mean((data$O3 - predictions_persistence)^2))


#lm.outBIC et lm.outBICint sont plus performants car :
#ils integrent plusieurs variables explicatives ,et donc capturer des patterns plus complexes
#la stratégie de persistance se contente d'utiliser la mesure d'ozone de la veille comme prédiction,
#ce qui peut être insuffisant pour capturer les variations et les facteurs qui peuvent influencer l'ozone,
#parexemple dans lm.out1 on a pas pu capturer tout au debut que DD SUd n'etait pas significatif



#Au final je peux proposer le modele lm.outBIC car il prend en compte des interaction entre les predicteurs qui peuvent echapper au autres plus qu'il a :bty

#un biais qui est plus reduit que les autes 
#precision des predictions
#inclusion des interactions