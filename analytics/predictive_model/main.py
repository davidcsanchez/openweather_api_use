import numpy as np
import pandas as pd
import matplotlib.pyplot as plt

plt.rcParams['figure.figsize'] = (16, 9)
plt.style.use('ggplot')
from sklearn import linear_model
from sklearn.metrics import mean_squared_error, r2_score

data = pd.read_csv("./Weather.csv", sep = ";")

xAxis = data["ts"].values
yAxis = data["temperature"].values
plt.scatter(xAxis,yAxis)

X_train = np.array(data[["ts"]])
y_train = data['temperature'].values

linear_model = linear_model.LinearRegression()

linear_model.fit(X_train, y_train)

predict = linear_model.predict(X_train)
print("Linear regression data:" )
print('Coefficient: \n', linear_model.coef_)

print('Independent term: \n', linear_model.intercept_)

print("Mean squared error: \n%.2f" % mean_squared_error(y_train, predict))

next_day=xAxis[-1]+86400000
next_day_temperature = linear_model.predict([[next_day]])

print('Next day temperature predict in celcius: \n', int(next_day_temperature))
plt.title('Temperature linear regression')
plt.plot(xAxis, xAxis*linear_model.coef_ + linear_model.intercept_, label='linear')
plt.savefig('Temperature_linear_regression.png')