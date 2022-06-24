# SCILib (Source Camera Identification Library)

The Source Camera Identification Java Library implemented by DIFLab@UniSA
Java Library for Source Camera Identification

## Description:
Java implementation of Residual Noise (RN) and Reference Pattern(RP) (or digital camera fingerprint) extraction using the Source Camera Identification (SCI) algorithm from Fridrich et al. (2006).

The SCI algorithm is a robust method for the identification of source cameras. It is based on the assumption that the source cameras are characterized by a set of reference patterns (RP) and a set of noise patterns (RN). The identification of the source cameras is based on the comparison of the RP and RN of the source cameras and is widely used in literature.
This implementation of  digital camera fingerprint extract from a set of color (RGB) images from a device C:
* the Residual Noise (RN) from each image
* the Reference Pattern (RP) of C

In addition the library can be used to the calculation of the statical correlation value between tho patterns (i.e. a couple of RPs or Rns or a mix of them).

## Installation:
**Install 1/2:** Add this to pom.xml:

```xml
<dependency>
  <groupId>it.unisa.di.dif</groupId>
  <artifactId>scilib</artifactId>
  <version>1.2.0</version>
</dependency>
```

**Install 2/2:** Run via command line
```shell
mvn install
```

## Usage: ....
See the GitHub Project [TesTSCIlib](https://github.com/DIFLabUnisa/TestSCILib) for a working example.

## Support: 
[diflab@unisa.it](mailto:diflab@unisa)

### Contribution:
* [Andrea Bruno](mailto:andbruno@unisa.it) - Implementation of the library
* [Paola Capasso](mailto:pcapasso@unisa.it) - Documentation

### Authors:
* Teams at [DIFLabUnisa](https://ifaselab.di.unisa.it/) of Prof. Giusepep Cattaneo

### Status of the project:
The project is complete of all the module useful to calculate  the Residual Noise(RN) and the Reference Pattern(RP).

### Test:
Tested with Java 8.

### License:
See the [LICENSE](LICENSE) file for details.

### References

To be done
