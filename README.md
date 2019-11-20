# calendar-shaper

An iCalendar &#128467; proxy that allows you to shape the data to your needs for a better integration in your beloved calendar client.

## How it works ?
calendar-shaper forwards all requests that it receive to the calendar server, receives the response, applies a set of rules and transformations and forwards it back to the client.

All configured calendars configured will be accessible using their name on the host.

There are 3 type of rules that allow you to process the data at a certain level of the calendar : for the whole file, at an event level and at a tag level.
Thus a deep personalisation is possible.

You can configure the calendars rule by editing the `calendars.json` file under the `config` folder.

## Build
calendar-shaper use Maven for its build, and can be simply built using the `package` job :
```bash
mvn clean compile package
```

## General configuration
You can configure the general properties of calendar-shaper by editing the `config/config.json` file.

List of properties available :

|Property|Description|Default value|
|---|---|---|
|`port`|The port the server will be started to|80|

## Calendars configuration
calendar-shaper can handle multiple calendars. Each one will be available from the outside using its name.

The `config/calendar.json` file represents a list of calendar object.
Each calendar is defined by a name, the server URL to forward requests and a list of rules.

Example of a calendar with an empty set of rules:
```json
{
    "name": "4-ifa",
    "url": "https://ade.insa-lyon.fr/4-ifa",
    "rules": [ ]
}
```

You can find example of calendars configuration under the [example](example) folder.

### The rules
There are 3 types of rules :
* `CalendarRule` are rules that apply on the whole calendar
* `EventRule` are rules that apply on each event of the calendar
* `TagRule` are rules that apply on each values of a certain tag within the event context

All rules are composed of a type and a list of transformation. Note that the `TagRule` has an additional property : the tag name.

Example of a TagRule with an empty set of transformations:

```json
{
    "type": "TagRule",
    "tag": "DESCRIPTION",
    "transformations": [ ]
}
```

### The transformations
The transformations are the concept that allows you modify your data according to your configuration.\
Each transformation is defined by a type and a set of parameters.

You can combined as many transformations as you want. All will be executed in the given order.

Example of a ReplaceTransformation :
```json
{
    "type": "Replace",
    "params": {
        "regex": "wrong",
        "replacement": "right"
    }
}
```

#### Remove
Removes all occurrences of a regex pattern found 

|Parameter|Description|
|---|---|
|`regex`|The pattern to be removed|

#### Replace
Replaces all occurrences of a regex pattern by a literal string.
 
|Parameter|Description|
|---|---|
|`regex`|The pattern to find|
|`replacement`|The string literal to replace to|

#### FindAndReplace
Replaces all occurrences of a regex pattern by the value of a replacement pattern.
You can specify the regex group to use for both pattern.
 
|Parameter|Description|
|---|---|
|`findRegex`|The pattern to find|
|`findGroupId`|The group index to replace inside the pattern|
|`replaceRegex`|The pattern that will replace the found one|
|`replaceGroupId`|The group index inside the replace pattern to use as a replacement|

#### Selection
Selects only a part of the data defined by a regex pattern.

|Parameter|Description|
|---|---|
|`regex`|The pattern to replace the data by|
|`groupId`|The group index inside the pattern|

#### TagValue
Replaces the data by the value of a given tag.

If you are in a `CalendarRule` the first occurrence of the tag will be used.\
If you are in a `TagRule` the value will be taken in the nearest `VEVENT` parent.

|Parameter|Description|
|---|---|
|`tag`|The tag to extract the value from|

#### SetValue
Replaces the data by a literal string.

|Parameter|Description|
|---|---|
|`value`|The value to replace the data by|

## Docker image
A docker image is available under the `docker` folder.\
It can be easily built using the `build.sh` script configured to your needs.

You probably want to access your configuration from the outside of the container.
You can create a volume pointing to `/opt/calendar-shaper/config` folder.

```bash
docker run -p 80:80 -v /my/cfg/fldr:/opt/calendar-shaper/config -d bmarsaud/calendar-shaper
```

## Dependencies
* Java 11
* [gson](https://github.com/google/gson) - JSON serialization/deserialization library
* [log4j](https://logging.apache.org/log4j/2.x/) - Logging library

## Contributors
Brought to you with ❤️ by :

|Name|Role|
|---|---|
|Bastien Marsaud|Developer|