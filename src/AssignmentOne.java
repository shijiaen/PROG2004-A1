import java.util.ArrayList;
import java.util.List;

/**
 * Runs a scripted demonstration of the health service appointment system.
 *
 * <p>There is no menu, login or user input. The main method creates the
 * objects, exercises their behaviour and prints the results, which is the
 * simulation required by the assessment brief. The demonstration follows the
 * six parts of the brief:</p>
 *
 * <ol>
 *   <li>Parts 1 and 2: the HealthProfessional hierarchy.</li>
 *   <li>Part 3: working with a mixed collection of health professionals.</li>
 *   <li>Part 4: modelling and printing a single appointment.</li>
 *   <li>Part 5: managing the appointment collection.</li>
 *   <li>Part 6: an end-to-end booking day, including error cases.</li>
 * </ol>
 *
 * @author &lt;Your Name&gt;
 * @version 1.0
 */
public class AssignmentOne {

    private static final String SECTION_LINE = "============================================================";
    private static final String SUB_SECTION_LINE = "------------------------------------------------------------";

    /**
     * Starts the demonstration.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        printSection("PROG2004 Assessment 1 - Health Service Appointment System");
        System.out.println("A scripted demonstration of the object-oriented backbone of a");
        System.out.println("health service appointment system. There is no menu or user");
        System.out.println("input: main creates the objects and exercises their behaviour.");

        ClinicRoster roster = createRoster();
        ClinicSchedule schedule = new ClinicSchedule("SCU Health Clinic");

        System.out.println();
        System.out.println("Parts 1 and 2 are the class design itself: HealthProfessional is");
        System.out.println("an abstract class holding the shared data, and the roster contains");
        System.out.println("GeneralPractitioner, Specialist, Nurse and Dietitian objects.");
        System.out.println("Part 3 exercises that design below.");

        demonstratePartThree(roster);
        demonstratePartFour(roster);
        demonstratePartFive(roster, schedule);
        demonstratePartSix(roster, schedule);

        printSection("END OF DEMONSTRATION");
    }

    /**
     * Creates the clinic roster used by the demonstration.
     *
     * <p>The roster contains three GPs and two of each other type. Different
     * constructor overloads are used so that the default Monday-to-Friday
     * roster is also demonstrated.</p>
     *
     * @return the populated roster
     */
    private static ClinicRoster createRoster() {
        ClinicRoster roster = new ClinicRoster();

        roster.addProfessional(new GeneralPractitioner(
                "1001", "Dr Alice Nguyen",
                days(Day.MONDAY, Day.WEDNESDAY, Day.FRIDAY),
                "MED-55821", true));
        roster.addProfessional(new GeneralPractitioner(
                "1002", "Dr Ben Carter",
                days(Day.TUESDAY, Day.THURSDAY),
                "MED-44108", false));
        // Uses the constructor that defaults to the Monday-to-Friday week.
        roster.addProfessional(new GeneralPractitioner(
                "1003", "Dr Priya Raman", "MED-77034"));

        roster.addProfessional(new Specialist(
                "2001", "Dr Sofia Marchetti",
                days(Day.MONDAY, Day.WEDNESDAY),
                "Cardiologist", "St Vincent's Hospital"));
        // Uses the constructor that defaults to the Monday-to-Friday week.
        roster.addProfessional(new Specialist(
                "2002", "Dr Daniel Kim", "Paediatrician", "SCU Health Clinic"));

        roster.addProfessional(new Nurse(
                "3001", "Grace Okafor",
                days(Day.MONDAY, Day.WEDNESDAY, Day.FRIDAY),
                "General Ward", "NMW-2210"));
        roster.addProfessional(new Nurse(
                "3002", "Liam Foster",
                days(Day.TUESDAY, Day.THURSDAY),
                "Outpatient Clinic", "NMW-3387"));

        roster.addProfessional(new Dietitian(
                "4001", "Hannah Lee",
                days(Day.MONDAY, Day.WEDNESDAY, Day.FRIDAY),
                "Diabetes nutrition"));
        // Uses the constructor that defaults to the Monday-to-Friday week.
        roster.addProfessional(new Dietitian(
                "4002", "Marco Rossi", "Sports nutrition"));

        return roster;
    }

    /**
     * Demonstrates Part 3: working with health professional objects.
     *
     * <p>The roster is printed through the common HealthProfessional type,
     * which shows that every subtype can be stored and processed together.
     * The Prescriber interface and the subtype-only methods are then
     * exercised.</p>
     *
     * @param roster the clinic roster
     */
    private static void demonstratePartThree(ClinicRoster roster) {
        printSection("PART 3 - WORKING WITH HEALTH PROFESSIONAL OBJECTS");

        System.out.println("The clinic roster holds " + roster.size() + " professionals in one");
        System.out.println("ArrayList<HealthProfessional>. The loop below uses only the common");
        System.out.println("supertype, yet each object prints its own type and details.");
        System.out.println();

        List<HealthProfessional> professionals = roster.getAllProfessionals();
        for (int i = 0; i < professionals.size(); i++) {
            System.out.println("[" + (i + 1) + "]");
            System.out.println(professionals.get(i));
            System.out.println();
        }

        printSubHeading("Availability stored in the shared HealthProfessional class");
        HealthProfessional gp = roster.findById("1001");
        System.out.println("  " + gp.getSummary());
        System.out.println("  Available days: " + gp.getAvailableDaysAsText());
        System.out.println("  Works on Monday: " + gp.isAvailableOn(Day.MONDAY));
        System.out.println("  Works on Sunday: " + gp.isAvailableOn(Day.SUNDAY));

        printSubHeading("The same objects treated through the Prescriber interface");
        List<Prescriber> prescribers = roster.getPrescribers();
        for (Prescriber prescriber : prescribers) {
            System.out.println("  " + prescriber.prescribe(
                    "Jane Smith", "Amoxicillin", "500 mg three times daily"));
        }

        printSubHeading("Behaviour that only a specific subtype provides");
        HealthProfessional nurse = roster.findById("3001");
        if (nurse instanceof Nurse) {
            Nurse specificNurse = (Nurse) nurse;
            System.out.println("  " + specificNurse.collectBloodSample("Jane Smith"));
            System.out.println("  " + specificNurse.recordVitalSigns("Jane Smith", 72, 36.8));
        }

        HealthProfessional dietitian = roster.findById("4001");
        if (dietitian instanceof Dietitian) {
            Dietitian specificDietitian = (Dietitian) dietitian;
            System.out.println("  " + specificDietitian.createMealPlan(
                    "Jane Smith", "lower cholesterol"));
            double bmi = specificDietitian.calculateBMI(82.0, 1.78);
            System.out.println("  BMI for an 82.0 kg, 1.78 m patient: "
                    + String.format("%.1f", bmi)
                    + " (" + specificDietitian.classifyBMI(bmi) + ")");
        }

        HealthProfessional specialist = roster.findById("2001");
        if (specialist instanceof Specialist) {
            Specialist specificSpecialist = (Specialist) specialist;
            System.out.println("  " + specificSpecialist.orderDiagnosticTest(
                    "Tom Baker", "echocardiogram"));
        }
    }

    /**
     * Demonstrates Part 4: modelling and printing a single appointment.
     *
     * @param roster the clinic roster
     */
    private static void demonstratePartFour(ClinicRoster roster) {
        printSection("PART 4 - MODELLING A SINGLE APPOINTMENT");

        HealthProfessional gp = roster.findById("1001");
        HealthProfessional specialist = roster.findById("2001");
        Patient jane = new Patient("Jane Smith", "0412 345 678");
        Patient tom = new Patient("Tom Baker", "0433 210 987");

        Appointment first = new Appointment(jane, gp, new TimeSlot(9, 0));
        Appointment second = new Appointment(tom, specialist, new TimeSlot(8, 30));

        System.out.println("Two appointments created for two different health professional types:");
        System.out.println();
        System.out.println(first);
        System.out.println();
        System.out.println(second);
        System.out.println();
        System.out.println("Comparable check: the 08:30 appointment sorts before the 09:00 appointment: "
                + (second.compareTo(first) < 0));
        System.out.println();
        System.out.println("An appointment cannot be created without a patient:");
        try {
            new Appointment(null, gp, new TimeSlot(10, 0));
        } catch (IllegalArgumentException error) {
            System.out.println("  Blocked: " + error.getMessage());
        }
    }

    /**
     * Demonstrates Part 5: managing the appointment collection.
     *
     * @param roster   the clinic roster
     * @param schedule the clinic schedule
     */
    private static void demonstratePartFive(ClinicRoster roster, ClinicSchedule schedule) {
        printSection("PART 5 - MANAGING THE COLLECTION OF APPOINTMENTS");

        AppointmentManager manager = new AppointmentManager(schedule);
        System.out.println("Clinic schedule: " + schedule);
        System.out.println("Time slots: " + schedule.getSlots());

        Patient jane = new Patient("Jane Smith", "0412 345 678");
        Patient tom = new Patient("Tom Baker", "0433 210 987");
        Patient aisha = new Patient("Aisha Khan", "0455 908 112");
        Patient liam = new Patient("Liam Chen", "0488 771 305");

        HealthProfessional gpNguyen = roster.findById("1001");
        HealthProfessional gpCarter = roster.findById("1002");
        HealthProfessional cardiologist = roster.findById("2001");
        HealthProfessional paediatrician = roster.findById("2002");
        HealthProfessional nurseOkafor = roster.findById("3001");
        HealthProfessional nurseFoster = roster.findById("3002");
        HealthProfessional dietitianLee = roster.findById("4001");

        printSubHeading("Adding appointments (mixed health professional types)");
        // Tom's 13:00 booking is added before his 08:30 booking so that the
        // later sort has something to correct.
        printMessage(manager.addAppointment(jane, gpNguyen, new TimeSlot(9, 0)));
        printMessage(manager.addAppointment(tom, cardiologist, new TimeSlot(13, 0)));
        printMessage(manager.addAppointment(tom, cardiologist, new TimeSlot(8, 30)));
        printMessage(manager.addAppointment(aisha, nurseOkafor, new TimeSlot(10, 0)));
        printMessage(manager.addAppointment(aisha, dietitianLee, new TimeSlot(11, 0)));
        printMessage(manager.addAppointment(liam, nurseFoster, new TimeSlot(9, 0)));
        printMessage(manager.addAppointment(liam, paediatrician, new TimeSlot(10, 0)));
        printMessage(manager.addAppointment(jane, gpCarter, new TimeSlot(14, 0)));

        printSubHeading("All appointments in insertion order");
        System.out.print(manager.formatAppointments(
                "All appointments as booked", manager.getAllAppointments()));

        printSubHeading("All appointments sorted by time");
        System.out.print(manager.formatAppointments(
                "All appointments sorted by time",
                manager.sortByTime(manager.getAllAppointments())));

        printSubHeading("Filtering by health professional ID (2001)");
        List<Appointment> cardiologistAppointments =
                manager.getAppointmentsForProfessional("2001");
        System.out.print(manager.formatAppointments(
                "Appointments for health professional 2001", cardiologistAppointments));

        printSubHeading("Sorting the filtered subset by time");
        System.out.print(manager.formatAppointments(
                "Health professional 2001, sorted by time",
                manager.sortByTime(cardiologistAppointments)));

        printSubHeading("Filtering by patient mobile number (0412 345 678)");
        System.out.print(manager.formatAppointments(
                "Appointments for mobile number 0412 345 678",
                manager.getAppointmentsForPatient("0412 345 678")));

        printSubHeading("Cancelling by health professional ID and time");
        printMessage(manager.cancelAppointment("1001", "09:00"));

        printSubHeading("Cancelling by patient mobile number");
        printMessage(manager.cancelAppointmentsByPhone("0455 908 112"));

        printSubHeading("Appointments after the cancellations");
        System.out.print(manager.formatAppointments(
                "Remaining appointments, sorted by time",
                manager.sortByTime(manager.getAllAppointments())));

        printSubHeading("Schedule flexibility: a different day, no code changes");
        ClinicSchedule shortDay = new ClinicSchedule("Saturday Clinic", 9, 0, 12, 0, 45);
        System.out.println("  New schedule: " + shortDay);
        System.out.println("  New slots: " + shortDay.getSlots());
        AppointmentManager shortDayManager = new AppointmentManager(shortDay);
        printMessage(shortDayManager.addAppointment(jane, gpNguyen, new TimeSlot(9, 0)));
        printMessage(shortDayManager.addAppointment(tom, gpCarter, new TimeSlot(9, 45)));
        printMessage(shortDayManager.addAppointment(aisha, dietitianLee, new TimeSlot(9, 30)));

        printSubHeading("Error cases");
        System.out.println("  Attempt to double-book a professional:");
        printMessage(manager.addAppointment(
                new Patient("Nina Patel", "0499 111 222"), gpCarter, new TimeSlot(14, 0)));

        System.out.println("  Attempt to book outside the clinic schedule:");
        printMessage(manager.addAppointment(
                new Patient("Nina Patel", "0499 111 222"), cardiologist, new TimeSlot(17, 0)));

        System.out.println("  Attempt to book without patient details:");
        printMessage(manager.addAppointment(null, gpNguyen, new TimeSlot(12, 0)));

        System.out.println("  Attempt to cancel an appointment that does not exist:");
        printMessage(manager.cancelAppointment("9999", "09:00"));

        System.out.println("  Attempt to cancel with an unknown phone number:");
        printMessage(manager.cancelAppointmentsByPhone("0400 000 000"));

        System.out.println("  Attempt to cancel with invalid time text:");
        printMessage(manager.cancelAppointment("1002", "not-a-time"));
    }

    /**
     * Demonstrates Part 6: the appointment system end-to-end.
     *
     * <p>A fresh clinic day is used so that the complete lifecycle is visible:
     * book, view, filter, sort, cancel, view again and handle errors.</p>
     *
     * @param roster   the clinic roster
     * @param schedule the clinic schedule
     */
    private static void demonstratePartSix(ClinicRoster roster, ClinicSchedule schedule) {
        printSection("PART 6 - END-TO-END DEMONSTRATION");

        System.out.println("A fresh clinic day is used so that the whole booking lifecycle is");
        System.out.println("shown from start to finish.");
        AppointmentManager manager = new AppointmentManager(schedule);

        Patient emma = new Patient("Emma Wilson", "0466 555 444");
        Patient noah = new Patient("Noah Brown", "0421 998 776");
        Patient olivia = new Patient("Olivia Martin", "0477 123 900");

        HealthProfessional gpRaman = roster.findById("1003");
        HealthProfessional cardiologist = roster.findById("2001");
        HealthProfessional nurseOkafor = roster.findById("3001");
        HealthProfessional dietitianLee = roster.findById("4001");

        printSubHeading("1. Booking the day");
        printMessage(manager.addAppointment(emma, dietitianLee, new TimeSlot(10, 30)));
        printMessage(manager.addAppointment(olivia, gpRaman, new TimeSlot(9, 0)));
        printMessage(manager.addAppointment(noah, nurseOkafor, new TimeSlot(9, 0)));
        printMessage(manager.addAppointment(emma, gpRaman, new TimeSlot(8, 30)));
        printMessage(manager.addAppointment(noah, cardiologist, new TimeSlot(11, 30)));

        printSubHeading("2. The whole day, sorted by time");
        System.out.print(manager.formatAppointments(
                "All appointments", manager.sortByTime(manager.getAllAppointments())));

        printSubHeading("3. Filter by health professional ID (3001)");
        System.out.print(manager.formatAppointments(
                "Appointments for health professional 3001",
                manager.getAppointmentsForProfessional("3001")));

        printSubHeading("4. Filter by patient mobile number (0466 555 444)");
        List<Appointment> emmaAppointments = manager.getAppointmentsForPatient("0466 555 444");
        System.out.print(manager.formatAppointments(
                "Appointments for mobile number 0466 555 444", emmaAppointments));

        printSubHeading("5. Sort the filtered subset by time");
        System.out.print(manager.formatAppointments(
                "Emma Wilson's appointments, sorted by time",
                manager.sortByTime(emmaAppointments)));

        printSubHeading("6. Cancel one appointment by professional ID and time");
        printMessage(manager.cancelAppointment("1003", "08:30"));

        printSubHeading("7. The day after the cancellation");
        System.out.print(manager.formatAppointments(
                "Remaining appointments, sorted by time",
                manager.sortByTime(manager.getAllAppointments())));

        printSubHeading("8. Error cases");
        System.out.println("  The same professional cannot be booked twice at 09:00:");
        printMessage(manager.addAppointment(
                new Patient("Nina Patel", "0499 111 222"), nurseOkafor, new TimeSlot(9, 0)));

        System.out.println("  An appointment that does not exist cannot be cancelled:");
        printMessage(manager.cancelAppointment("1003", "15:00"));
    }

    /**
     * Prints a main section heading.
     *
     * @param title the heading text
     */
    private static void printSection(String title) {
        System.out.println();
        System.out.println(SECTION_LINE);
        System.out.println(title);
        System.out.println(SECTION_LINE);
    }

    /**
     * Prints a sub-section heading.
     *
     * @param title the sub-heading text
     */
    private static void printSubHeading(String title) {
        System.out.println();
        System.out.println(SUB_SECTION_LINE);
        System.out.println(title);
        System.out.println(SUB_SECTION_LINE);
    }

    /**
     * Prints a message returned by the appointment manager.
     *
     * @param message the message to print
     */
    private static void printMessage(String message) {
        System.out.println("  " + message);
    }

    /**
     * Builds a list from the supplied days.
     *
     * @param days the days to include
     * @return a new list containing the days in the supplied order
     */
    private static List<Day> days(Day... days) {
        List<Day> dayList = new ArrayList<>();
        for (Day day : days) {
            dayList.add(day);
        }
        return dayList;
    }
}
