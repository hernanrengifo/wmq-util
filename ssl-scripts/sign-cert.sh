#!/bin/sh
#
# Signs a certificate request using your already
# created CA key.
#
# The script requirers two arguments
# sign-cert.sh <queue manager name> <key store database>
#
# Below, you should change some general configuration
#
# Expiration time in days (e.g. 5*365 => 1825)
expiration=1825

qm=$1

qm_lower=`echo $qm | tr "[:upper:]" "[:lower:]"`

pw=$2


echo "CA signs certificate request"
gsk7cmd -cert -sign -db ca_key.kdb -pw $pw -label "CA_Cert" -sernum $RANDOM -file $qm_lower.p10 -target $qm_lower.p7r -expire $expiration


