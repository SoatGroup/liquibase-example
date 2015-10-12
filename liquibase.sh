#!/bin/bash

CURRENT_FILE=$(readlink -f $0)
CURRENT_DIR=$(dirname $CURRENT_FILE)
PROGNAME=${0##*/}

# Go in the liquibase migration module
cd "$CURRENT_DIR/soat-parent-code/sample-liquibase"

# Scripts variables :
SCRIPT_USAGE=0;
LIQUIBASE_OPTS="";

# Help function to print the program help
function usage()
{
	echo -e "$PROGNAME\n";
	echo -e "NOM\n\t"$PROGNAME" - Operation for liquibase\n";
	echo -e "SYNOPSIS\n\t"$PROGNAME" [OPTIONS] GOAL\n\t"$PROGNAME" [-h|--help] [-Ddetail=true]: print this help\n";
	echo -e "GOALS";
	output=$(mvn $LIQUIBASE_OPTS liquibase:help | egrep -v '\[INFO\]' | awk '/^liquibase:/,/\\n/');
	for g in $(echo "$output" | egrep -io '^liquibase\:[a-z]+$' | sed -r 's/^liquibase:([a-z]+)$/\1/gi'); do
		cmd_detail=$(echo "$output" | awk '/^liquibase:'$g'$/ {flag=1;next} /^\s*liquibase:.+$/{flag=0} flag {print}');
		echo -e "- $g : \n$cmd_detail\n" | sed -r 's/^([^\s].+)$/\t\1/g';
	done
	echo -e "OPTIONS";
	echo -e "\t-h|--help : Print this help";
	echo -e "\t-D : Set a maven options. This could be a liquibase option for example : -Dliquibase.verbose=true";
	echo -e "\t-L : Set a liquibase options. For example : -Lverbose=true";
}

# Parse command line
TEMP=$(getopt -o "hD:L:" --long "help" -n $PROGNAME -- "$@");
if [ $? != 0 ] ; then echo "Terminating..." >&2 ; exit 1 ; fi
eval set -- "$TEMP"
while true ; do
	case "$1" in
		-h|--help) SCRIPT_USAGE=1; shift ;;
		-D) LIQUIBASE_OPTS="${LIQUIBASE_OPTS} -D$2"; shift 2 ;;
		-L) LIQUIBASE_OPTS="${LIQUIBASE_OPTS} -Dliquibase.$2"; shift 2 ;;
		--) shift ; break ;;
		*) echo "Internal error!" ; exit 1 ;;
	esac
done

# Basic checks (help & command exist)
if [ $SCRIPT_USAGE != 0 ] ; then usage; exit 0; fi
if [[ $1 == "" ]] ; then u=$(usage); echo "$u">&2 ; exit 1 ; fi

# Execute maven goal for liquibase plugin with only the required log parsing
echo -e "Execute command '$1' on database :\n";
start_date=$(date +%s);
mvn $LIQUIBASE_OPTS resources:resources liquibase:$1| awk '/liquibase-maven-plugin/ {flag=1;next} /BUILD\s+SUCCESS/{flag=0} flag {print}';
echo -e "\nCommand end with status $? into "$(( $(date +%s) - $start_date ))" seconds"; 

exit $?;

