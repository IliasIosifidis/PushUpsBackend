package org.pushups.gymgoers.init

import net.datafaker.Faker
import org.pushups.gymgoers.model.Booking
import org.pushups.gymgoers.model.GymClass
import org.pushups.gymgoers.model.Member
import org.pushups.gymgoers.repository.BookingRepository
import org.pushups.gymgoers.repository.GymClassRepository
import org.pushups.gymgoers.repository.MemberRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import kotlin.random.Random

@Component
class DataInitializer(
    private val memberRepository: MemberRepository,
    private val bookingRepository: BookingRepository,
    private val gymClassRepository: GymClassRepository
) : CommandLineRunner {


    override fun run(vararg args: String) {
        val faker = Faker()


//         FAILSAFE TO NOT RUN AGAIN IF IT DID ONCE
        if (memberRepository.count() > 0 || gymClassRepository.count() > 0 || bookingRepository.count() > 0) return

        // FAKE MEMBERS
        val members = (1..1000).map {
            memberRepository.save(
                Member(
                    firstName = faker.name().firstName(),
                    lastName = faker.name().lastName(),
                    email = faker.internet().emailAddress(),
                    phoneNumber = faker.phoneNumber().cellPhone()
                )
            )
        }

        // CREATING CLASSES
        val classTypes = listOf(
            Triple("Aerobic", LocalTime.of(11, 0), LocalTime.of(13, 0)),
            Triple("Groups", LocalTime.of(13, 0), LocalTime.of(15, 0)),
            Triple("Calisthenics", LocalTime.of(15, 0), LocalTime.of(17, 0)),
            Triple("CrossFit", LocalTime.of(17, 0), LocalTime.of(19, 0)),
            Triple("Yoga", LocalTime.of(19, 0), LocalTime.of(21, 0))
        )

        // ENUM THE WORKING WEEKDAYS
        val weekdays = listOf(
            DayOfWeek.MONDAY,
            DayOfWeek.TUESDAY,
            DayOfWeek.WEDNESDAY,
            DayOfWeek.THURSDAY,
            DayOfWeek.FRIDAY
        )


        // CREATING THE GYM CLASSES
        val gymClasses = weekdays.flatMap { day ->
            classTypes.map { (name, start, end) ->
                gymClassRepository.save(
                    GymClass(
                        name = name,
                        dayOfTheWeek = day,
                        startTime = start,
                        endTime = end,
                        maxCapacity = 20,
                    )
                )
            }
        }

        // FAKING MEMBER BOOKINGS
        val random = Random
        val startDate = LocalDate.now().minusYears(1)

        (0L until 365).forEach { dayOffset ->
            val date = startDate.plusDays(dayOffset)
            val dayOfWeek = date.dayOfWeek

            if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) return@forEach

            val todaysClasses = gymClasses.filter { it.dayOfTheWeek == dayOfWeek }

            todaysClasses.forEach { gymClass ->
                val attendees = members.shuffled().take(random.nextInt(8, 20))

                attendees.forEach { member ->
                    try {
                        bookingRepository.save(
                            Booking(
                                member = member,
                                gymClass = gymClass,
                                date = date,
                                bookedAt = date.minusDays(random.nextLong(1, 8))
                                    .atTime(random.nextInt(8, 20), random.nextInt(0, 60))
                            )
                        )
                    } catch (e: Exception) {
                        println(e.message)
                        return
                    }

                }
            }
        }
        println("Seeded: ${memberRepository.count()} members, ${gymClassRepository.count()} classes, ${bookingRepository.count()} bookings")
    }
}