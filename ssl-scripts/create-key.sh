#!/bin/sh
#
# Creates a key database and a key for the provided
# queue manager.
# The script requirers two arguments
# create-keys.sh <queue manager name> <key store database>
#

qm=$1

# Below, you should change some general configuration
#
# The DN to use
dn="CN=$qm, O=ACME, C=SE"

# Expiration time in days (e.g. 5*365 => 1825)
expiration=1825



qm_lower=`echo $qm | tr "[:upper:]" "[:lower:]"`

pw=$2



echo "Removes directory for QM if it exists"
rm -rdf $qm_lower

echo "Creates directory for QM"
mkdir $qm_lower

echo "Creating key database"
gsk7cmd -keydb -create -db $qm_lower/key.kdb -pw $pw -type cms -expire $expiration -stash

echo "Creating certificate request in key database"
gsk7cmd -certreq -create -db $qm_lower/key.kdb -pw $pw -label ibmwebspheremq$qm_lower -dn $dn -file $qm_lower.p10

echo "CA signs certificate request"
gsk7cmd -cert -sign -db ca_key.kdb -pw $pw -label "CA_Cert" -sernum $RANDOM -file $qm_lower.p10 -target $qm_lower.p7r -expire $expiration

echo "Adding CA cert to key database"
gsk7cmd -cert -add -db $qm_lower/key.kdb -pw $pw -label "CA_Cert" -file ca_cert.crt

echo "Adding signed QM cert to key database"
gsk7cmd -cert -receive -db $qm_lower/key.kdb -pw $pw -file $qm_lower.p7r

echo "Cleaning up temporary files"
rm $qm_lower.p7r
rm $qm_lower.p10

