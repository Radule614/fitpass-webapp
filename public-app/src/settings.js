let distribution_build = false




let serverUrl = "http://localhost:9999";
if(distribution_build){
  serverUrl = "";
}

export default {
  serverUrl: serverUrl
}