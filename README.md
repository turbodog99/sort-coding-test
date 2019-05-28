# sort-coding-test

Takes delimiter-separated files as input. If started in server mode, starts a REST server
on port 3000 to allow requesting the loaded records in various sort orders.

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

## REST Server

The REST server always runs on port 3000

* POST /records - Post a single data line in any of the 3 formats supported by your existing code
* GET /records/gender - returns records sorted by gender
* GET /records/birthdate - returns records sorted by birthdate
* GET /records/name - returns records sorted by name

The POST route requires a delimiter=delimiter_name parameter where delimeter_name is one of
comma, pipe, or space.

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

In the interest of saving time, I did less testing around the actual loading of files based
on command-line parameters than I would do in production. I wrote tests to validate that they
did, in fact, put the filenames in the correct options slots, but I didn't write tests to
load the files again. I thought the best approach would be to refactor the file generation
stuff I did for testing the file loading and then creating test files and validating the loaded
records.

Many of the file-loading tests round trip the same data using different delimiters. On failure,
these tests will output which delimiter was being tested to keep it from being cryptic.

## Complications

The specification was ambiguous about whether each file would be tested separately or
whether all three would be loaded at once. This should cover both bases.

It doesn't detect whether the given file actually contains the given delimiters. This goes
back to the delimiter detection problem, but if the wrong delimiter type is specified,
it parses as a single field.

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
