import org.sourcegrade.jagr.gradle.task.grader.GraderRunTask

plugins {
    alias(libs.plugins.algomate)
}

exercise {
    assignmentId.set("h02")
}

submission {
    // ACHTUNG!
    // Setzen Sie im folgenden Bereich Ihre TU-ID (NICHT Ihre Matrikelnummer!), Ihren Nachnamen und Ihren Vornamen
    // in Anführungszeichen (z.B. "ab12cdef" für Ihre TU-ID) ein!
    studentId = "hl58ryry"
    firstName = "Hao"
    lastName = "Liu"

    // Optionally require own tests for mainBuildSubmission task. Default is false
    requireTests = false
}

dependencies {
    // libs.fopbot method generated from ./gradle/libs.versions.toml
    implementation(libs.fopbot)
}

tasks {
    withType<GraderRunTask> {
        doFirst {
            throw GradleException("No public tests are provided for this exercise! For more information go to https://moodle.informatik.tu-darmstadt.de/mod/page/view.php?id=60388")
        }
    }
}
