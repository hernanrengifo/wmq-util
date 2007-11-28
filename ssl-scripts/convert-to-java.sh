#!/bin/sh
#
# Converts the key store for a queue manager
# for use in Java applications (JKS key store)
#
# The script requirers two arguments
# convert-to-java.sh <queue manager name> <key store database>
#

qm=$1
qm_lower=`echo $qm | tr "[:upper:]" "[:lower:]"`

pw=$2

gsk7cmd -keydb -convert -db $qm_lower/key.kdb -pw $pw -old_format cms -new_format jks
