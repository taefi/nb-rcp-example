
# How to Build and Run

Run the following command in the root directory to build the project:
```shell
mvn clean install
```
This will build the whole project.

Then, to run it using the maven plugin, run the following command:
```shell
 mvn -f app/pom.xml nbm:run-platform -Dnetbeans.run.params="-J--add-opens=java.base/java.net=ALL-UNNAMED"
```
And for running it as a standalone executable, in Linux/macOS run the `/app/target/user_mgmt/bin/user_mgmt`, and in Windows run the `app/target/user_mgmt/bin/user_mgmt.exe` (or the x64 version):
```shell
/app/target/user_mgmt/bin/user_mgmt -J--add-opens=java.base/java.net=ALL-UNNAMED
```
The `-J--add-opens=java.base/java.net=ALL-UNNAMED` is needed to run the application without facing any issues regarding the access to `java.net` package.
If you later see other inaccessible packages, you can add them to the command similar to the one above.

During the execution of `mvn install`, the `nbm:standalone-zip` is executed automatically, that creates the minimal standalone executable under the `app/target` directory, named as `app-x.x-SNAPSHOT.zip`.
If you want to just do the packaging separately, execute the following command:
```shell
mvn -f app/pom.xml nbm:standalone-zip
```
