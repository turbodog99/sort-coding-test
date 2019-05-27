# sort-coding-test

Takes delimiter-separated files as input. If started in server mode, starts a REST server
on port 8080 to allow requesting the loaded records in various sort orders.

If not started in server mode, displays the loaded data in the following formats:

* sorted by gender ascencing then by last name ascending
* sorted by birth data ascending
* sorted by last name descending

## Usage

    `$ lein run [options]`

## Options

*  `-c, --comma-delimited-file FILENAME  comma delimited filename`
*  `-p, --pipe-delimited-file FILENAME   pipe delimited filename`
*  `-s, --space-delimited-file FILENAME  space delimited filename`
*  `    --start-server                   Start REST Server`

File options can be specified multiple times to load multiple files.

## Examples

For file display:

`lein run -c temp/test-comma-delimited-people.csv -p temp/test-pipe-delimited-people.csv`

Server mode:

`lein run -c temp/test-comma-delimited-people.csv -p temp/test-pipe-delimited-people.csv --start-server`

## Simplifications

It just prints records in the 3 requested sort orders. A properly useful app would take options
to choose the output format and sort order with a specified delimiter.

I chose passing the delimiter type for the loaded files as command-line options because
delimiter detection is a separate, inexact problem on its own.

## License

Copyright © 2019 Mark Herman, II

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
