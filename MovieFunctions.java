import java.util.*;

class MovieFunctions{
    public MovieFunctions(){}

    List<String> movies = new ArrayList();
    List<Double> prices = new ArrayList();
    List<Time> showTimes = new ArrayList();

    public void deconstruct(ArrayList<String> list){
        for(String entry : list){
            StringTokenizer st = new StringTokenizer(entry, ",");

            String price = st.nextToken();
            prices.add(Double.parseDouble((price.substring(price.indexOf("$") + 1, price.length()-1))));

            String showTime = st.nextToken();
            showTimes.add(new Time(showTime.substring(showTime.indexOf("=") + 1, showTime.length()-1)));

            String movie = st.nextToken();
            movies.add(movie.substring(movie.indexOf("=") + 1, movie.length()-1));
        }
    }


    public String getMoviesFromString(String movieName){
        String lowerMovieName = movieName.toLowerCase();
        for(String movie : movies)
            if((movie.toLowerCase()).indexOf(lowerMovieName) > -1)
                return movie;

        return null;
    }

    public String getMoviesFromString(String movieName, int start){
        String lowerMovieName = movieName.toLowerCase();
        for(int i = start; i < movies.size(); i++)
            if((movies.get(i).toLowerCase()).indexOf(lowerMovieName) > -1)
                return movies.get(i);

        return null;
    }

    public List<String> getAllMoviesFromString(String movieName){
        List<String> allMoviesWithName = new ArrayList<>();
        String lowerMovieName = movieName.toLowerCase();

        for(String movie : movies)
            if((movie.toLowerCase()).indexOf(lowerMovieName) > -1)
                allMoviesWithName.add(movie);

        return allMoviesWithName;
    }

    public int findCheapestMovie(){
        //Find cheapest in general

        int minIdx = -1;
        double minPrice = Double.MAX_VALUE;

        for(int i = 0; i < movies.size(); i++)
            if(prices.get(i) < minPrice){
                minIdx = i;
                minPrice = prices.get(i);
            }

        return minIdx;
    }
    public int findCheapestMovie(Time startTime, Time endTime){
        //Find based on time

        int minIdx = -1;
        double minPrice = Double.MAX_VALUE;

        for(int i = 0; i < movies.size(); i++)
            if(prices.get(i) < minPrice && showTimes.get(i).inRange(startTime, endTime)){
                minIdx = i;
                minPrice = prices.get(i);
            }

        return minIdx;
    }

    public int findCheapestMovie(String movieName){
        //Find based on name

        int minIdx = -1;
        double minPrice = Double.MAX_VALUE;

        for(int i = 0; i < movies.size(); i++){
            if((movies.get(i).toLowerCase()).indexOf(movieName.toLowerCase()) > -1 && prices.get(i) < minPrice){
                minIdx = i;
                minPrice = prices.get(i);
            }
        }

        return minIdx;
    }
}

class Time{
    private int month, date, year, hour, minute;

    public Time(){
        month = date = year = hour = minute = 0;
    }

    public Time(String time){
        //splits time in "mm/dd/yy/hr:min:sec" format
        StringTokenizer st = new StringTokenizer(time, "/");
        month = Integer.parseInt(st.nextToken());
        date = Integer.parseInt(st.nextToken());
        year = Integer.parseInt(st.nextToken());
        String utcTime = st.nextToken();

        StringTokenizer splitTime = new StringTokenizer(utcTime, ":");
        hour = Integer.parseInt(splitTime.nextToken());
        minute = Integer.parseInt(splitTime.nextToken());
    }

    public int getMonth(){        return month;    }
    public int getDate(){        return date;    }
    public int getYear(){        return year;    }
    public int getHour(){        return hour;    }
    public int getMinute(){        return minute;    }

    public boolean inRange(Time start, Time end){
        if(year > start.getYear() && year < end.getYear())
            return true;

        if(year == start.getYear() && year == end.getYear()){
            if(month > start.getMonth() && month < end.getMonth())
                return true;

            if(month == start.getMonth() && month == end.getMonth()){
                if(date > start.getDate() && date < end.getDate())
                    return true;

                if(date == start.getDate() && date == end.getDate()){
                    if(hour > start.getHour() && hour < end.getHour())
                        return true;

                    if(hour == start.getHour() && hour == end.getHour())
                        return minute >= start.getMinute() && minute <= end.getMinute();

                    else if(hour == start.getHour())
                        return minute >= start.getMinute();
                    else if(hour == end.getHour())
                        return minute <= end.getMinute();
                }

                else if(date == start.getDate()){
                    if(hour > start.getHour())
                        return true;

                    if(hour == start.getHour())
                        return minute >= start.getMinute();
                }
                else if(date == end.getDate()){
                    if(hour == end.getHour())
                        return true;

                    if(hour < end.getHour())
                        return minute <= end.getHour();
                }
            }

            else if(month == start.getMonth()){
                if(date > start.getDate())
                    return true;

                if(date == start.getDate()){
                    if(hour > start.getHour())
                        return true;

                    if(hour == start.getHour())
                        return minute >= start.getMinute();
                }
            }
            else if(month == end.getMonth()){
                if(date < end.getDate())
                    return true;

                if(date == end.getDate()){
                    if(hour == end.getHour())
                        return true;

                    if(hour < end.getHour())
                        return minute <= end.getHour();
                }
            }
        }

        else if(year == start.getYear()){
            if(month > start.getMonth())
                return true;

            if(month == start.getMonth()){
                if(date > start.getDate())
                    return true;

                if(date == start.getDate()){
                    if(hour > start.getHour())
                        return true;

                    if(hour == start.getHour())
                        return minute >= start.getMinute();
                }
            }
        }
        else if(year == end.getYear()){
            if(month < end.getMonth())
                return true;

            if(month == end.getMonth()){
                if(date < end.getDate())
                    return true;

                if(date == end.getDate()){
                    if(hour == end.getHour())
                        return true;

                    if(hour < end.getHour())
                        return minute <= end.getHour();
                }
            }
        }

        return false;
    }
}

