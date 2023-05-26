data = read.table("DataTP.txt",header = TRUE)
data_aix = head(subset(data,STATION="Aix"))

stat_o_p = summary(data_aix)
#compare the average of O3o and O3p 
var.test(stat_o_p$O3o,stat_o_p$O3p)
#rep : NON
#coeffiecient de correlation
coef_corr = cor(data_aix$O3p,data_aix$O3o)

#linear model between O3o and O3p : O3o = a + b.O3p + e
model = lm(O3o~O3p,data=data_aix)
summary(model)
#plot the points cluster and the regression line
plot(data_aix$O3p,data_aix$O3o)
abline(model,col="red")


plot(data_aix$O3o)
points(data_aix$O3p,col="blue")
points(model$fitted.values,col="red")
#comparer dans une meme fenetre , les histogrames des differents predicteurs quantitatifs
par(mfrow=c(2,2))
hist(data_aix$O3p)
hist(data_aix$NO2)
hist(data_aix$NO)
hist(data_aix$NOx)



