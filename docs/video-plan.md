# Five-minute video plan

**Requirement:** a video of no more than 5 minutes that names each of the four
pillars of OOP, explains each concept in your own words, points to the exact
place in your code where it is used, and justifies why you used it there.

This plan is a guide, not a script to read. Rehearse it in your own words and
make sure every claim matches the code you actually submit.

> **Recording aid:** a silent 4:40 demo video,
> `A1-25259230-demo-silent.mp4`, has been generated to sit behind this
> explanation. The exact chapter timestamps are in
> `docs/video-narration-cues.md`. Record your own voice over the video and
> keep the final cut under 5:00.

---

## Before recording

- Run `AssignmentOne` once and have the console output ready to show.
- Open the four main files in tabs: `HealthProfessional.java`,
  `GeneralPractitioner.java`, `Appointment.java`, `AssignmentOne.java`.
- Close unrelated windows and silence notifications.
- Check the microphone level and do a 20-second test recording.
- Keep a clock visible; the hard limit is 5:00. Aim for 4:40.

---

## Suggested timing

| Time | Content |
|---|---|
| 0:00 - 0:15 | Name, unit, assessment, and what the program is. |
| 0:15 - 0:40 | Run `main`; show the roster output and the appointment list. |
| 0:40 - 1:35 | Pillar 1: Encapsulation. |
| 1:35 - 2:30 | Pillar 2: Abstraction. |
| 2:30 - 3:30 | Pillar 3: Inheritance. |
| 3:30 - 4:30 | Pillar 4: Polymorphism. |
| 4:30 - 5:00 | Where the four pillars work together; close. |

---

## 0:00 - 0:40 - Introduction and context

Say:

- your name and the assessment;
- the system is the object-oriented backbone of a clinic appointment system;
- there is no UI or database because the brief asks for classes and a scripted
  `main` demonstration;
- the main classes are `HealthProfessional`, its four subclasses, `Patient`,
  `Appointment`, `TimeSlot`, `ClinicSchedule` and `AppointmentManager`.

Show: run `AssignmentOne` and let the Part 3 roster and one sorted appointment
list appear.

---

## 0:40 - 1:35 - Encapsulation

**Name it:** encapsulation.

**Explain in your own words:**

- bundling data and the behaviour that operates on it inside one class;
- hiding the fields so outside code cannot put the object into an invalid
  state;
- exposing only safe operations through public methods.

**Point to the code:**

- `HealthProfessional.java`: the `private final` fields `id`, `name` and
  `availableDays`;
- the constructor that validates the ID, name and roster;
- `getAvailableDays()` returns `new ArrayList<>(availableDays)`, a copy, so a
  caller cannot modify the internal list;
- `Patient.java`: the constructor removes phone separators, checks for exactly
  ten digits and stores the cleaned value;
- `Appointment.java`: the constructor rejects `null` patient, professional or
  time.

**Justify:**

- every object is valid from the moment it is created;
- there are no setters, so it stays valid;
- the defensive copy stops aliasing bugs;
- the manager can rely on `Appointment` always having all three parts.

---

## 1:35 - 2:30 - Abstraction

**Name it:** abstraction.

**Explain in your own words:**

- focusing on what is common and essential, and hiding the details;
- in Java, abstract classes and interfaces let the design state *what* must be
  done without saying *how* each subtype does it.

**Point to the code:**

- `HealthProfessional` is `abstract` and declares two abstract methods:
  `getProfessionalType()` and `getRoleDetails()`;
- a plain `HealthProfessional` cannot be instantiated, which matches the
  domain: there is no "generic" health professional;
- `Prescriber` is an interface with one method, `prescribe(...)`. Only the GP
  and the specialist implement it; the nurse and dietitian do not;
- `ClinicSchedule` hides the loop that generates slots. Callers only ask
  `containsSlot(...)` or `getSlots()`.

**Justify:**

- the base class captures shared state but cannot know each subtype's type or
  role details;
- forcing subclasses to implement the abstract methods means every subtype is
  guaranteed to provide them;
- prescribing is not common to all professionals, so it belongs in an
  interface rather than the base class;
- the schedule can change without the booking code knowing how slots are
  generated.

---

## 2:30 - 3:30 - Inheritance

**Name it:** inheritance.

**Explain in your own words:**

- a subclass acquires the fields and methods of a superclass and adds or
  overrides behaviour;
- it models an "is-a" relationship.

**Point to the code:**

- `GeneralPractitioner`, `Specialist`, `Nurse` and `Dietitian` all
  `extends HealthProfessional`;
- each subclass calls `super(id, name, availableDays)` as its first statement;
- the shared fields, validation, `toString()`, `equals()` and `hashCode()` are
  written once in the base class;
- each subclass adds only its own fields, for example
  `Specialist.specialty` and `Nurse.ward`;
- the overloaded constructors: `new GeneralPractitioner("1003", "Dr Priya
  Raman", "MED-77034")` uses the base class default of Monday-to-Friday.

**Justify:**

- avoids duplicating ID, name, roster and validation in four classes;
- a change to the shared rule is made in one place;
- the subclass constructors guarantee the shared part is set up correctly
  before the subclass adds its own data.

---

## 3:30 - 4:30 - Polymorphism

**Name it:** polymorphism.

**Explain in your own words:**

- one reference type can refer to objects of different subclasses;
- the object that actually runs decides which overridden method is executed;
- the same call can produce different behaviour.

**Point to the code:**

- `AssignmentOne.demonstratePartThree()` stores all nine professionals in one
  `ArrayList<HealthProfessional>` and loops over them with a single
  `System.out.println(professional)`;
- `toString()` is inherited but calls the overridden
  `getProfessionalType()` and `getRoleDetails()`, so each object prints its own
  type and details;
- the `List<Prescriber>` loop calls `prescribe(...)` on every doctor through the
  interface;
- `Appointment implements Comparable<Appointment>` and
  `Collections.sort(...)` sorts a mixed list of appointments by time through
  the common `compareTo` contract.

**Justify:**

- the roster loop does not need an `if` for each type and would not change if a
  fifth type were added;
- interface polymorphism limits prescribing to the types that actually support
  it;
- sorting through `Comparable` means the manager does not need to know how to
  compare appointments itself.

---

## 4:30 - 5:00 - Bringing the pillars together and closing

Say:

- encapsulation makes the objects safe to use;
- abstraction and inheritance remove duplication and enforce the contract;
- polymorphism lets the system process the whole team and the whole day
  through common types;
- the result is a design where adding a new professional type or changing the
  clinic schedule requires little or no change to existing code.

Stop before 5:00. Do not add a code walkthrough of every method; the rubric is
about the four pillars, not a full tour.

---

## Rehearsal checklist

- [ ] Each pillar is **named** clearly.
- [ ] Each pillar is **explained in your own words** (not read from a
      definition).
- [ ] Each pillar is **located in the code** on screen.
- [ ] Each pillar has a **reason** for being used in that situation.
- [ ] The recording is **4:40 or less**.
- [ ] The console demonstration actually runs.
- [ ] Audio is clear and the code font is large enough to read.
- [ ] The video is uploaded to SCU OneDrive and the link is set to "anyone with
      the link can view".
- [ ] The link is pasted into the MySCU submission comments.
