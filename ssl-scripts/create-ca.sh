#!/bin/sh
#
# Creates a key database and a key for your root CA cert.
# Use this key to sign future keys
# The script requirers one argument
# create-ca.sh <key store database>
#

# Below, you should change some general configuration
#
# The DN to use
dn=CN=Example CA cert,O=ACME,C=SE

# Expiration time in days (e.g. 5*365 => 1825)
expiration=3650

pw=$1

gsk7cmd -keydb -create -db ca_key.kdb -pw $pw -type cms -expire $expiration
gsk7cmd -cert -create -db ca_key.kdb -pw $pw -label "CA_Cert" -dn "$dn" -expire $expiration
gsk7cmd -cert -extract -db ca_key.kdb -pw $pw -label "CA_Cert" -target ca_cert.crt

