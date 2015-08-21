Send a message using Skype API inside a Apache Ant Task.
Current features are:

  * Send a message to one nickname
  * Send a message to a list of nicknames separated by a char (comma, pipe, etc.)

Below you can see the correct use of the parameters with the chosen action:

| **Parameter** | **Type** | **Default** | **Note** |
|:--------------|:---------|:------------|:---------|
| **message**   | M        | n/d         | The message |
| **listofnicknames** | M        | n/a         | The list of Skype's nicknames |
| **separator** | O        | comma (,)   | The separator used in the parameter _listofnicknames_ |
| **debug**     | O        | false       | Flag for enabling the debug |
| **failonerror** | O        | true        | It fails in case of an exception |

M=Mandatory, O=Optional, n/a=Not Allowed, n/d=Not Defined