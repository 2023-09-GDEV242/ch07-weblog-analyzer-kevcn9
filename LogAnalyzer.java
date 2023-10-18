/**
 * Read web server data and analyse hourly, monthly and daily accesses. 
 * 
 * @author David J. Barnes and Michael Kölling.
 * @version    2016.02.29
 */
public class LogAnalyzer
{
    private int[] hourCounts;
    
    private int[] dayCounts;
    
    private int[] weekCounts;
    
    private int[] monthCounts;
    
    private LogfileReader reader;

    /**
     * Create an object to analyze a file name.
     * @param fileName locates the file name of a given file 
     */
    public LogAnalyzer(String fileName)
    { 
        hourCounts = new int[24];
        
        dayCounts = new int[366];
        
        weekCounts = new int[7];
        
        monthCounts = new int[13];
        
        reader = new LogfileReader(fileName);
        
    }
    
    /**
     * the max accessed for hour based on the analyzed data logs 
     * @return the hour 
     */
    public int busiestHour()
    {
        int busiestHour = 0;
        int busiest = 0;
        for(int hour = 0; hour < hourCounts.length; hour++)
        {
            if(hourCounts[busiestHour] < hourCounts[hour])
            {
                busiest = hourCounts[hour];
                busiestHour = hour;
            }
        }
        return busiestHour;
    }
    
    /**
     * the least accessed for hour based on the analyzed data logs 
     * @return the hour 
     */
    public int quietestHour()
    {
        int leastBusyHour = numberOfAccesses();
        int quietestHour = 0;
        for(int hour = 0; hour < hourCounts.length; hour++)
        {
            if(hourCounts[hour] < leastBusyHour)
            {
                quietestHour = hour;
                leastBusyHour = hourCounts[hour];
            } 
        }
        return leastBusyHour;
    }
    
    /**
     * gives the max accessed based on a two hour period from the analyzed data 
     * @return the first hour from the two hour period 
     */
    public int busiestTwoHour()
    {
        int busiestStartHour = 0;
        int maxAccesses = 0;
    
        for (int hour = 0; hour < hourCounts.length - 1; hour++) 
        {
            int accesses = hourCounts[hour] + hourCounts[hour + 1];
            if (accesses > maxAccesses) 
            {
                maxAccesses = accesses;
                busiestStartHour = hour;
            }
        }
        
        int accesses = hourCounts[23] + hourCounts[0];
        if (accesses > maxAccesses) 
        {
            busiestStartHour = 23;
        }
        return busiestStartHour;
    }
    
    /**
     * gives the max accessed for day based on the analyzed data logs from a 7 day cycle
     * @return the busiest day
     */
    public int busiestDay()
    {
        int busiestDay = 0;
        int busiest = 0;
        for(int day = 1; day < weekCounts.length; day++)
        {
            if(weekCounts[day] > busiest)
            {
                busiestDay = day;
            }
        }
        return busiestDay;
    }
    
    /**
     * gives the least accessed for day based on the analyzed data logs from a 7 day cycle
     * @return the quietest day
     */
    public int quietestDay()
    {
        int quietest = numberOfAccesses();
        int quietestDay = 0;
        for(int day = 1; day < weekCounts.length; day++)
        {
            if(dayCounts[day] < quietest)
            {
                quietest = weekCounts[day];
                quietestDay = day;
            } 
        }
        return quietestDay;
    }
    
    /**
     * total amount of accesses per month
     * @return number of accesses
     */
    public int totalAccessesPerMonth()
    {
        int totalAccesses = 0;
        for(int month = 0; month < monthCounts.length; month++) 
        {
            totalAccesses += monthCounts[month];
        }
        return totalAccesses;
    }
    
    /**
     * gives the max accessed for month based on the analyzed data logs
     * @return the busiest month 
     */
    public int busiestMonth()
    {
        int busiestMonth = 0;
        int busiest = 0;
        for(int month = 0; month < monthCounts.length; month++)
        {
            if(monthCounts[busiestMonth] < monthCounts[month])
            {
                busiestMonth = month;
                busiestMonth = month;
            }
        }
        return busiestMonth;
    }
    
    /**
     * gives the least accessed month based on the analyzed data logs
     * @return the quietest month 
     */
    public int quietestMonth()
    { 
        int leastBusyMonth = totalAccessesPerMonth();
        int quietestMonth = 0;
        for(int month = 1; month < monthCounts.length; month++)
        {
            if(monthCounts[month] < leastBusyMonth)
            {
                quietestMonth = month;
                leastBusyMonth = monthCounts[month];
            } 
        }
        return quietestMonth;
    }
    
    /**
     * gives the months average accesses from the total accessed every month
     * @return the average every month
     */
    public double averageAccessesPerMonth()
    {
        int totalAccesses = 0;
        int monthsWithAccesses = 0;
        for(int month = 0; month < monthCounts.length; month++)
        {   
            totalAccesses += monthCounts[month];
            if (monthCounts[month] > 0) 
            {
                monthsWithAccesses++;
            }
        }
        
        double average = 0.0;
        average = (double) 
        totalAccesses / monthsWithAccesses;
        return average;
    }
    
    /**
     * gives total accesses on each log data 
     * @return the total amount of accesses
     */
    public int numberOfAccesses()
    {
        int totalAccesses = 0;

        for(int hour = 0; hour < hourCounts.length; hour++) 
        {
            totalAccesses += hourCounts[hour];
        }
        return totalAccesses;
    }

    /**
     * Analyze the hourly, daily and monthly access data from the log file.
     */
    public void analyzeData()
    {
        while(reader.hasNext()) 
        {
            LogEntry entry = reader.next();
            int hour = entry.getHour();
            int day = entry.getDay();
            int month = entry.getMonth();
            hourCounts[hour]++;
            dayCounts[day]++;
            monthCounts[month]++;
        }
    }
    
    /**
     * Print the hourly counts.
     */
    public void printHourlyCounts()
    {
        System.out.println("Hr: Count");
        int hour = 0;
        while(hour < hourCounts.length)
        {
            System.out.println(hour + ": " + 
            hourCounts[hour]);
            hour++;
        }
    }
    
    /**
     * print the day counts.
     */
    public void printDayCounts()
    {
        System.out.println("Day: Count");
        int day = 0;
        while(day < dayCounts.length)
        {
            System.out.println(day + ": " + 
            dayCounts[day]);
            day++;
        }
    }
    
    /**
     * print the monthly counts 
     */
    public void printMonthlyCounts()
    {
        System.out.println("Month: Count");
        int month = 0;
        while(month < monthCounts.length)
        {
            System.out.println(month + ": " + 
            monthCounts[month]);
            month++;
        }
    }
    
    /**
     * this analyzes the week pattern based on the daily access counts
     * @return the week counts  
     */
    public int[] analyzeWeekPattern()
    {
        for(int day = 0; day < 7; day++) 
        { 
            weekCounts[day] = dayCounts[day];
        }
        weekCounts[0] += dayCounts[365];
        return weekCounts;
    }
    
    /**
     * Print the lines of data read by the LogfileReader
     */
    public void printData()
    {
        reader.printData();
    }
}
