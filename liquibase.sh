#!/bin/bash

CURRENT_FILE=$(readlink -f $0)
CURRENT_DIR=$(dirname $CURRENT_FILE)
PROGNAME=${0##*/}

echo "Going in the liquibase maven module"
cd "$CURRENT_DIR/soat-parent-code/sample-liquibase"

function usage()
{
	echo -e "Help of $PROGNAME\n\n";
	output=$(mvn liquibase:help | egrep -v '\[INFO\]' | awk '/^liquibase:/,/\\n/');
	for g in $(echo "$output" | egrep -io '^liquibase\:[a-z]+$' | sed -r 's/^liquibase:([a-z]+)$/\1/gi'); do
		# This following command will include the first line which is not required...
		#cmd_detail=$(echo "$output" | sed -n '/^liquibase:'$g'$/,/^\s*$/p');
		cmd_detail=$(echo "$output" | awk '/^liquibase:'$g'$/ {flag=1;next} /^\s*$/{flag=0} flag {print}')
		echo -e "Command '$g' : \n$cmd_detail\n";
	done
}

TEMP=$(getopt -o "hD:L:" --long "help" -n $PROGNAME -- "$@");
if [ $? != 0 ] ; then echo "Terminating..." >&2 ; exit 1 ; fi
eval set -- "$TEMP"

# Set the default maven options
MVN_OPTS="";

while true ; do
	case "$1" in
		-h|--help) usage ; exit 0; shift ;;
		-D) MVN_OPTS="${MVN_OPTS} -D$2"; shift 2 ;;
		-L) MVN_OPTS="${MVN_OPTS} -Dliquibase.$2"; shift 2 ;;
		--) shift ; break ;;
		*) echo "Internal error!" ; exit 1 ;;
	esac
done

echo -e "Execute command '$1' on database :\n";
start_date=$(date +%s);
mvn $MVN_OPTS resources:resources liquibase:$1 -Pdb-migration | awk '/liquibase-maven-plugin/ {flag=1;next} /BUILD\s+SUCCESS/{flag=0} flag {print}';
echo -e "\nCommand end with status $? into "$(( $(date +%s) - $start_date ))" seconds"; 


