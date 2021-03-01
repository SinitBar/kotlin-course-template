using System;
enum MonthName
{
    January,
    February,
    March,
    April,
    May,
    June,
    July,
    August,
    September,
    October,
    November,
    December
}

namespace OOP3LabaWhatDay
{
    class WhatDay
    {
        static void Main(string[] args)
        {
            Console.WriteLine("Please, enter the year");
            string line = Console.ReadLine();
            int yearNum = int.Parse(line);
            bool isLeapYear = false;
            int maxDayNum = 365;
            if (yearNum % 4 == 0)
            { 
                isLeapYear = true;
                maxDayNum++;
            }
            Console.WriteLine("Please, enter the day number between 1 and {0}: ", maxDayNum);
            line = Console.ReadLine();
            try
            {
                int dayNum = int.Parse(line);
                // dayNum = sum of all days of the months before + now data

                if (dayNum > 365 || dayNum < 1)
                    throw new ArgumentOutOfRangeException("Day out of Range");
            
                int monthNum = 0;
                if (isLeapYear)
                {
                    foreach (int daysInLeapMonth in DaysInLeapMonths)
                    {
                        if (dayNum <= daysInLeapMonth)
                            break;
                        else
                        {
                            dayNum -= daysInLeapMonth;
                            monthNum++;
                        }
                    }
                }
                else 
                {
                    foreach (int daysInMonth in DaysInMonths)
                    {
                        if (dayNum <= daysInMonth)
                            break;
                        else
                        {
                            dayNum -= daysInMonth;
                            monthNum++;
                        }
                    }
                }

                //if (dayNum <= 31) // January
                //    goto End;
                //else
                //{
                //    dayNum -= 31;
                //    monthNum++;
                //}

                //if (dayNum <= 28 || (dayNum <= 29 && isLeapYear)) // February
                //    goto End;
                //else
                //{
                //    dayNum -= 28;
                //if (isLeapYear)
                //    dayNum--;
                //    monthNum++;
                //}

                //if (dayNum <= 31)  // March
                //    goto End;
                //else
                //{
                //    dayNum -= 31;
                //    monthNum++;
                //}

                //if (dayNum <= 30) // April
                //    goto End;
                //else
                //{
                //    dayNum -= 30;
                //    monthNum++;
                //}

                //if (dayNum <= 31)  // May
                //    goto End;
                //else
                //{
                //    dayNum -= 31;
                //    monthNum++;
                //}

                //if (dayNum <= 30)  // June
                //    goto End;
                //else
                //{
                //    dayNum -= 30;
                //    monthNum++;
                //}

                //if (dayNum <= 31)  // July
                //    goto End;
                //else
                //{
                //    dayNum -= 31;
                //    monthNum++;
                //}

                //if (dayNum <= 31)  // August
                //    goto End;
                //else
                //{
                //    dayNum -= 31;
                //    monthNum++;
                //}

                //if (dayNum <= 30) // September
                //    goto End;
                //else
                //{
                //    dayNum -= 30;
                //    monthNum++;
                //}

                //if (dayNum <= 31)  // October
                //    goto End;
                //else
                //{
                //    dayNum -= 31;
                //    monthNum++;
                //}

                //if (dayNum <= 31) // November
                //    goto End;
                //else
                //{
                //    dayNum -= 30;
                //    monthNum++;
                //}

                //if (dayNum <= 31) // December
                //    goto End;
                //else
                //{
                //    dayNum -= 31;
                //    monthNum++;
                //}

                //End:

                //MonthName temp = (MonthName)monthNum;
                //string monthName = temp.ToString();

                //string monthName;
                //switch (monthNum)
                //{
                //    case 0:
                //        monthName = "January"; break;
                //    case 1:
                //        monthName = "February"; break;
                //    case 2:
                //        monthName = "March"; break;
                //    case 3:
                //        monthName = "April"; break;
                //    case 4:
                //        monthName = "May"; break;
                //    case 5:
                //        monthName = "June"; break;
                //    case 6:
                //        monthName = "July"; break;
                //    case 7:
                //        monthName = "August"; break;
                //    case 8:
                //        monthName = "September"; break;
                //    case 9:
                //        monthName = "October"; break;
                //    case 10:
                //        monthName = "November"; break;
                //    case 11:
                //        monthName = "December"; break;
                //    default:
                //        monthName = "not done yet"; break;
                //}

                MonthName temp = (MonthName)monthNum;
                string monthName = temp.ToString();
                Console.WriteLine("{0} {1}", monthName, dayNum);
            }

            catch (ArgumentOutOfRangeException wrong_data)
            {
                Console.WriteLine("Error: {0} \nStack: {1}", wrong_data.Message, wrong_data.StackTrace);
            }
        }

        static System.Collections.ICollection DaysInMonths
            = new int[12] { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
        static System.Collections.ICollection DaysInLeapMonths
            = new int[12] { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
    }
}