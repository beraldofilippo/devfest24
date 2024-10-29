package com.beraldo.protobufexample

import com.beraldo.protos.AddressBook
import com.beraldo.protos.Person

object ProtobufsTest {
    val john : Person = Person.newBuilder()
        .setId(1234)
        .setName("John Doe")
        .setEmail("jdoe@example.com")
        .addPhones(
            Person.PhoneNumber.newBuilder()
                .setNumber("555-4321")
                .setType(Person.PhoneType.MOBILE)
                .build()
        )
        .build()

    val addressBook: AddressBook = AddressBook.newBuilder()
        .addPeople(john)
        .build()
}

class FieldPresenceTest {

    fun test() {
        /**
         * Example on how optional generates field presence utility methods.
         *
         * optional string email = 3;
         * */
        val johnHasEmail: Boolean = ProtobufsTest.john.hasEmail()

        /**
         * Example on how absence of optional does not generate utility methods and we cannot
         * easily determine whether
         *
         * string name = 1;
         *
         * was explicitly set or not, best we can do is check whether the string is empty or not
         * (since default value for string is empty string "").
         * */
        val johnHasName : Boolean = ProtobufsTest.john.name.isNotEmpty()

        /**
         * Example on field presence for repeated fields, default value is always empty list.
         *
         * repeated PhoneNumber phones = 4;
         * */
        val johnHasPhoneNumbers : Boolean = ProtobufsTest.john.phonesList.isNotEmpty()

        /**
         * Example on field presence for message fields by default
         *
         * PhoneNumber emergency_phone = 5;
         *
         * is exactly as doing
         *
         * optional PhoneNumber emergency_phone = 5;
         * */
        val johnHasEmergencyPhoneNumber: Boolean  = ProtobufsTest.john.hasEmergencyPhone()

        /**
         * But actually it is commonly bypassed using this check against the message being a
         * default instance (which **LIKELY** means it was not set).
         * */
        val johnHasEmergencyPhoneNumberVariant: Boolean = ProtobufsTest.john.emergencyPhone != Person.PhoneNumber.getDefaultInstance()
    }
}