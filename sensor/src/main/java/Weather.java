import java.time.Instant;

public class Weather {
    private Instant ts;
    private Location location;
    private String weather;
    private double temp;
    private double wind;
    private double windDirection;
    private double humidity;
    private double pressure;

    public Instant getTs() {
        return ts;
    }

    public void setTs(Instant ts) {
        this.ts = ts;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getWind() {
        return wind;
    }

    public void setWind(double wind) {
        this.wind = wind;
    }

    public double getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(double windDirection) {
        this.windDirection = windDirection;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }



    public static class Builder{

        private Weather weather;

        public Builder() {
            this.weather = new Weather();
            }

        public Builder ts(Instant date) {
            weather.ts = date;
            return this;
        }

        public Builder location(Location location) {
            weather.location =  location;
            return this;
        }

        public Builder weather(String weather) {
            this.weather.weather = weather;
            return this;
        }

        public Builder temp(double temp) {
            weather.temp =  temp;
            return this;
        }

        public Builder wind(double wind) {
            weather.wind = wind;
            return this;
        }

        public Builder winddirection(double winddirection) {
            weather.windDirection = winddirection;
            return this;
        }

        public Builder humidity(double humidity) {
            weather.humidity = humidity;
            return this;
        }

        public Builder pressure(double pressure) {
            weather.pressure = pressure;
            return this;
        }

        public Weather build() {return weather;
        }
    }
}