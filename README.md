kappaeg
=======

An example set of streaming projects conforming to both the Lambda and Kappa architecture patterns. Generally used as a template for demos or implementations. Right now this is a work in progress. 

To use simply clone the repo and follow the directions below:

1. Install HDP with Storm OR use the Hortonworks Sandbox found here: http://hortonworks.com/products/hortonworks-sandbox/
2. Install the latest Kafka release compiled with Scala 2.10
3. Generate your own Twitter API keys, the free ones will do. Here: https://apps.twitter.com/
4. Modify the CDRStormContext properties to reflect your own environment. The defaults work if you run everything on one sandbox
5. Start up all services in HDP as well as Kafka
6. Create your Kafka topics as in the misc_commands.txt
7. Deploy the compiled uber jar as a topology
8. Execute the CDRTestDataProducer (this will drive load into Kafka and eventually Storm) from the uber jar


Note: The "Kappa Architecture" is a term coined by Jay Kreps, one of the senior LinkedIn Architects. 
