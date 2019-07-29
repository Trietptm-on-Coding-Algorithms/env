# scp permissions

```bash
# [client]
scp foo@bar:/a/b/*
unable to identify /a/b: permission denied
# [server]
chmod 755 /a/b
```

# plink

Debug with pseudo terminal: `-t`

http://www.straightrunning.com/puttymanual/Chapter7.html#plink-option-antispoof

# pscp

```ps1
$id='foo_user@foo_host';
$dir='\\foo';
cd ${dir};
& ${dir}\opt\putty\pscp -i ${dir}\.ssh\foo.ppk -r ${id}:/tmp/foo .
```

# Generate key pair

```bash
id='foo_user@foo_host' && \
    ssh-keygen -b 2048 -t rsa -f ~/.ssh/"$id" -q -C "" -N ""
id='foo_user@foo_host' && \
    ~/opt/putty/PUTTYGEN.EXE ~/.ssh/"$id" -O private -o ~/.ssh/"$id".ppk

id='foo_user@foo_host' && \
    ssh-copy-id -i ~/.ssh/"$id".pub "$id"
# ||
mkdir -p ~/.ssh/
chmod 700 ~/.ssh
touch ~/.ssh/authorized_keys
chmod 600 ~/.ssh/authorized_keys
printf 'ssh-rsa AAAA/.../ZZZZ' >> ~/.ssh/authorized_keys
# ||
cat ~/.ssh/"$id".pub >> ~/.ssh/authorized_keys
```

# Debug - Login script at the remote end produce garbage on stdout

```bash
ssh foo_user@foo_pass /bin/true | xxd
```

# Validation

Requires `Subject Alternative Name`

https://wiki.openssl.org/index.php/Hostname_validation
=> CN or alt

hostname conditional
https://stackoverflow.com/a/33555404

DNS resolves to a different IP (internal address) that does not contain a valid certificate chain (no trusted CA anchor, no valid hostname, missing SAN)
    workaround - request to the outside IP from inside the network
    https://mail.google.com/mail/u/0/#inbox/FMfcgxwChcnGPTtBHPqXnjsWVFMQJGCN

# References

https://developer.android.com/training/articles/security-ssl.html#CommonHostnameProbs
https://tools.ietf.org/html/rfc5280

https://stackoverflow.com/questions/2308774/httpget-with-https-sslpeerunverifiedexception
https://github.com/vt-middleware/ldaptive/blob/master/core/src/main/java/org/ldaptive/ssl/DefaultHostnameVerifier.java
