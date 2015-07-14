Documentation
=============

New Version functionalities
---------------------------

### Links:

1. energy/stat/{type}/{deviceID}/{date}
The result is an ArrayList of EnergyAbstract objects.
It returns 24 objects.
They are values from the __type__ table for a __device__ from a __date__.
Every object contains an average value from one hour of that day. If the datas are missing for some hours, they are being predicted.
There must be at least 2 results from database for this to work or else it will return an empty ArrayList.

2. energy/stat/{type}/{deviceID}/{dateFrom}/{dateTo}
The result is ArrayList of EnergyAbstract objects.
It returns X objects where X is the difference in days between __dateFrom__ and __dateTo__.
They are values from the __type__ table for a __device__ from period of time between __dateFrom__ and __dateTo__.
Every object contains an average value from one day of that period of time. If the datas are missing for some days, they are being predicted.
There must be at least 2 results from database for this to work or else it will return empty ArrayList.

3. energy/stat/latest/{deviceID}
The result is an ArrayList of EnergyAbstract objects.
It return 6 objects.
They are values from the t_data_latest table, where are stored latest results of a different data types.

We are taking results only for:
    * total\_active\_consumed
    * total\_active\_produced
    * total\_active\_power
    * total\_reactive\_consumed
    * total\_reactive\_produced
    * total\_reactive\_power


Old Version functionalities
---------------------------

### Links:

1. energy/data/{type}
The result is an ArrayList of EnergyAbstract objects.
The URL can contain parameter - __limit__.
It returns X objects where X is the value of parameter __limit__.
if __limit__ is not set, then by default it returns 10 objects.
The objects are the first X values from the __type__ table.

2. energy/data/{type}/{deviceID}
The result is an ArrayList of EnergyAbstract objects.
The URL can contain parameter - __limit__.
It returns X objects where X is the value of parameter __limit__.
if __limit__ is not set, then by default it returns 10 objects.
The objects are the first X values from the __type__ table for the __device__.

3. energy/data/{type}/{deviceID}/{date}
The result is an ArrayList of EnergyAbstract objects.
The URL can contain parameter - __limit__.
It returns X objects where X is the value of parameter __limit__.
if __limit__ is not set, then by default it returns 10 objects.
The objects are the first X values from the __type__ table for the __device__ from a __date__.

4. energy/data/{type}/{deviceID}/{datefrom}/{dateto}
The result is an ArrayList of EnergyAbstract objects.
The URL can contain parameter - __limit__.
It returns X objects where X is the value of parameter __limit__.
if __limit__ is not set, then by default it returns 10 objects.
The objects are the first X values from the __type__ table for the __device__ from a period of time between __dateFrom__ and __dateTo__.

5. meters
The result is an ArrayList of EnergyAbstract objects.
Each of the objects is a different device and its last value of a type = active\_power or total\_active\_power.
It returns all the meters from database, which have data in the table t_data_latest.

6. datasets
It returns an array of EnergyTypesEnum.
It's an enum that represents available data types (see: Parameters->{type}).

7. predict/{deviceID}
The result is an ArrayList of EnergyAbstract objects.
The URL can contain 2 parameters - __limit__ and __days__.
It returns X objects where X is the value of parameter __days__.
if __days__ is not set, then by default it returns 7 objects.
The values of objects are being predicted based on Y results from database where Y is the value of parameter __limit__.

Parameters
----------

1. {deviceID}:
It's a String type variable, it represents the id of the meter we want to get data for.
For example:
    * 152522786
    * 3642118178
    * portoantico_1502_4

2. {type}:
It's a Enum type variable, it represents the table we will be taking the data from.
Also it tells us which data we want to get.
The List of available types:
    * ACTIVE\_CONSUMED\_1
    * ACTIVE\_CONSUMED\_2
    * ACTIVE\_CONSUMED\_3
    * ACTIVE\_PRODUCED\_1
    * ACTIVE\_PRODUCED\_2
    * ACTIVE\_PRODUCED\_3
    * ACTIVE\_POWER\_1
    * ACTIVE\_POWER\_2
    * ACTIVE\_POWER\_3
    * REACTIVE\_CONSUMED\_1
    * REACTIVE\_CONSUMED\_2
    * REACTIVE\_CONSUMED\_3
    * REACTIVE\_PRODUCED\_1
    * REACTIVE\_PRODUCED\_2
    * REACTIVE\_PRODUCED\_3
    * REACTIVE\_POWER\_1
    * REACTIVE\_POWER\_2
    * REACTIVE\_POWER\_3
    * TOTAL\_ACTIVE\_CONSUMED
    * TOTAL\_ACTIVE\_PRODUCED
    * TOTAL\_ACTIVE\_POWER
    * TOTAL\_REACTIVE\_CONSUMED
    * TOTAL\_REACTIVE\_PRODUCED
    * TOTAL\_REACTIVE\_POWER

3. {date}, {dateFrom}, {dateTo}:
They are Date types. Their format is "yyyy-MM-dd".
They represent a day, from which we want to get data.
dateFrom and dateTo are used together, they represent a period of time between these days.
For example:
    * 2015-04-10
    * 2013-07-07