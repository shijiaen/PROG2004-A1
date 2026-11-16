# Narration cue sheet - `A1-25259230-demo-silent.mp4`

**Video:** 4:40, silent, 1920x1080, 30 fps.
**Your job:** record your own voice over it. Do not read this file word for
word; use the prompts and speak in your own words.

---

## Before recording

- Watch the video once without speaking.
- Rehearse each chapter with a timer. The cut is 4:40, so you have 20 seconds
  of headroom before the 5:00 limit.
- Use a quiet room, test the microphone, and close notifications.
- Keep the code open in your editor: `HealthProfessional.java`,
  `GeneralPractitioner.java`, `Appointment.java`, `AssignmentOne.java`.

## Recording options

### Clipchamp (easiest on Windows)

1. Open Clipchamp and create a new video.
2. Import `A1-25259230-demo-silent.mp4` and drag it to the timeline.
3. Click **Record** > **Audio**, play the timeline and speak.
4. Export at 1080p (or 720p) as MP4.

### OBS Studio

1. Add the MP4 as a **Media Source** (loop off).
2. Add your microphone as an audio source.
3. Record while the video plays, then export MP4.

### PowerPoint

1. Insert > Video > This Device, and place the video full-slide.
2. Slide Show > Record, and record narration while it plays.
3. Export as a video.

---

## Timeline

### 0:00 - 0:12 | Title

On screen: title, your name, and the four pillars.

Cover:

- your name and student number;
- the assignment: the object-oriented backbone of a health service
  appointment system;
- there is no UI, login or database: `AssignmentOne.main` creates the objects
  and prints a scripted simulation;
- the explanation will follow the four pillars.

### 0:12 - 0:35 | Design at a glance

On screen: the inheritance tree and the collaboration chain.

Cover:

- `HealthProfessional` is the abstract base for four types: GP, specialist,
  nurse and dietitian;
- `Patient`, `TimeSlot` and `HealthProfessional` feed an `Appointment`;
- `AppointmentManager` stores the day in an `ArrayList<Appointment>`;
- `ClinicSchedule` defines the valid time slots;
- point out the separation: the professional hierarchy is separate from
  appointment management.

### 0:35 - 1:05 | Part 3 - nine professionals, one collection

On screen: real Part 3 console output for a GP, specialist, nurse and
dietitian.

Cover:

- the roster holds nine objects in one `ArrayList<HealthProfessional>`;
- the loop calls `System.out.println(professionals.get(i))`; there is no
  `if` statement per type;
- each object prints its own type and role details because `toString()` calls
  the overridden `getProfessionalType()` and `getRoleDetails()`;
- adding a fifth professional type would not change this loop.

### 1:05 - 1:30 | Part 5/6 - managing the day

On screen: appointments sorted by time, then success and warning messages.

Cover:

- appointments are sorted with `Comparable`; same-time appointments for
  different professionals keep their insertion order;
- filtering works by professional ID and by patient phone number;
- cancelling works by professional ID plus time, and by phone number;
- double bookings and invalid slots return warnings instead of corrupting the
  collection;
- the list is displayed again after a cancellation to show the update.

### 1:30 - 2:15 | Encapsulation

On screen: `HealthProfessional` fields, constructor and getter.

Cover:

- all fields are `private final`; there are no setters;
- the constructor validates the digit-only ID, the name and the roster;
- `Patient` separately validates a 10-digit mobile number;
- `getAvailableDays()` returns a copy, so callers cannot change the internal
  list;
- result: an object cannot be created invalid and cannot become invalid later;
- why: the manager can trust every appointment it receives.

### 2:15 - 3:00 | Abstraction

On screen: the abstract methods, `toString()` hook and the `Prescriber`
interface.

Cover:

- `HealthProfessional` is abstract, so a "generic" professional cannot be
  created;
- every subtype must implement `getProfessionalType()` and `getRoleDetails()`;
- the base `toString()` calls those methods, so shared printing works for all
  types;
- `Prescriber` is a separate interface implemented only by the GP and the
  specialist; nurses and dietitians do not inherit prescribing they do not
  have;
- `ClinicSchedule` hides slot generation; the booking code only asks whether a
  slot is valid;
- why: contracts without forcing irrelevant behaviour.

### 3:00 - 3:45 | Inheritance

On screen: `GeneralPractitioner extends HealthProfessional implements
Prescriber`.

Cover:

- the subclass declaration and the `super(id, name, availableDays)` call;
- the base constructor validates and stores the shared fields once;
- the subclass adds only the Medicare provider number, bulk-billing flag and
  its own type label;
- all four subtypes reuse the shared ID, name, roster, `toString`, `equals`
  and `hashCode`;
- the overloaded constructor defaults to Monday-to-Friday;
- why: reuse and one place for shared rules.

### 3:45 - 4:30 | Polymorphism

On screen: the `List<HealthProfessional>` loop, the `List<Prescriber>` loop
and `Comparable`.

Cover:

- one `List<HealthProfessional>` loop calls `println`; the actual object
  decides which overridden methods run;
- one `List<Prescriber>` loop calls `prescribe()` on both doctor types through
  the interface;
- `Appointment implements Comparable`; `Collections.sort` uses `compareTo`;
- the same reference type produces different behaviour for different objects;
- why: mixed collections, sorting and extensibility.

### 4:30 - 4:40 | Wrap-up

On screen: the four-pillar recap and the repository link.

Cover:

- one sentence per pillar;
- a new professional type needs one new subclass;
- a new schedule needs different `ClinicSchedule` arguments;
- thank the viewer and mention the GitHub repository.

---

## Check before uploading

- [ ] Your voice is clear and the video/voice stay in sync.
- [ ] The final duration is **under 5:00** (the provided cut is 4:40).
- [ ] The export is 1080p or 720p.
- [ ] The video is uploaded to SCU OneDrive and the link is set to
      "anyone with the link can view".
- [ ] The link is pasted into the MySCU submission comments.
- [ ] The GenAI declaration mentions any AI assistance used for the visual
      slides. The narration and explanation must be your own.

> Integrity note: these slides are a visual aid generated with AI assistance.
> The assessment requires **your** explanation of the four pillars. Rehearse,
> understand every claim, and declare the assistance honestly.
