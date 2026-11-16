# Submission checklist - PROG2004 A1

**Due:** Monday of Week 4, 16 November 2026, 11:59 pm
**Weight:** 40%, individual
**Resubmission:** not available after the deadline; multiple submissions are
allowed before the deadline.

---

## 1. Personalise the project

- [x] Project folder named `A1-25259230`.
- [x] Javadoc `@author` tags filled in as Shi Jiaen (25259230).
- [x] Main class is `AssignmentOne` in `src`.
- [ ] Read every file and make sure you understand and agree with each design
      decision. You may be interviewed about the code.

## 2. Build and test

- [ ] Compile with no errors:

      ```bash
      javac -d out src/*.java
      java -cp out AssignmentOne
      ```

- [ ] Confirm the output covers:
  - [ ] Part 3: mixed roster printed through the common type.
  - [ ] Part 4: two appointments printed with professional details.
  - [ ] Part 5: add, view, filter by professional ID, filter by phone, sort all,
        sort a filtered subset, cancel by ID + time, cancel by phone.
  - [ ] Part 5: warnings for double booking, invalid slot, missing patient,
        unknown appointment, unknown phone, invalid time text.
  - [ ] Part 6: end-to-end day with a cancellation and both error cases.
  - [ ] Schedule flexibility: the 45-minute Saturday schedule works.

- [ ] Delete the `out` folder (or leave it, but do not include it in the zip).

## 3. GitHub

- [x] Repository created: https://github.com/shijiaen/PROG2004-A1 (public).
- [x] Logical commits created with descriptive messages.
- [x] Repository pushed to `main`.
- [x] Repository is public, so the tutor and assessor can view it without an invite.
- [ ] Copy the repository link into the MySCU submission comments.

**Important:** the history must honestly reflect your own work. Do not
backdate commits or fabricate activity.

## 4. WakaTime

- [ ] Install the WakaTime plugin for your IDE (or the WakaTime CLI).
- [ ] Track the time you spend on this Java project.
- [ ] Export or screenshot the report.
- [ ] Name the file `A1-25259230-WakaTime.png` (or `.jpg` / `.pdf`).
- [ ] Check that the report is readable and covers the assessment period.

## 5. Video

- [ ] Follow `docs/video-plan.md`.
- [ ] Keep it **under 5 minutes** (aim for 4:40).
- [ ] Name and explain all four pillars, point to the code, and justify each
      choice.
- [ ] Upload to your SCU OneDrive.
- [ ] Create a shareable link that the tutor and assessor can open.
- [ ] Paste the link into the MySCU submission comments.

## 6. Package the zip

- [ ] The zip is called `A1-25259230.zip`.
- [ ] It contains the project folder with `src/`, `README.md` and `docs/`.
- [ ] It does **not** contain `out/`, `.idea/`, `.class` files or local
      screenshots that are not part of the submission.
- [ ] Test the zip: extract it to a new folder and compile again.

## 7. MySCU submission comments

Include:

- [ ] GitHub repository link (with collaborators invited if private).
- [ ] Video link.
- [ ] GenAI declaration. State honestly which tools were used and for what
      (for example code scaffolding, explanation, documentation). Confirm that
      you reviewed, tested and can explain the submitted code.
- [ ] Any other information required by your tutor.

## 8. Final integrity check

- [ ] Only concepts from Modules 1-3 are used. No streams, lambdas, records,
      `Optional`, databases, UI frameworks or external libraries.
- [ ] Every class and public method has Javadoc, and non-obvious logic has an
      inline comment.
- [ ] The code runs and the output is formatted clearly.
- [ ] You can explain:
  - [ ] why `HealthProfessional` is abstract;
  - [ ] why `Prescriber` is an interface;
  - [ ] why `equals`/`hashCode` use the professional's ID;
  - [ ] why appointments are stored in an `ArrayList`;
  - [ ] why `TimeSlot` is comparable;
  - [ ] how the schedule can be changed without changing the manager.

- [ ] Submit the zip, WakaTime report and comments before 11:59 pm.
