import React, { useState } from "react";
import { Input } from "@/components/ui/input";
import { Textarea } from "@/components/ui/textarea";
import { Button } from "@/components/ui/button";
import {
    Accordion,
    AccordionItem,
    AccordionTrigger,
    AccordionContent,
} from "@/components/ui/accordion";

const ContactFormAndFAQ = () => {
    const [formData, setFormData] = useState({
        name: "",
        email: "",
        subject: "",
        message: "",
    });

    const [response, setResponse] = useState("");

    const handleInputChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        // Możesz rozszerzyć tą część i podpiąć pod backend
        console.log("Data submitted: ", formData);

        // Aktualnie wysyła dane tylko do konsoli i wyświetla wiadomość
        setResponse("Your email has been sent successfully!");
        setTimeout(() => setResponse(""), 5000);
    };

    return (
        <div className="flex flex-col gap-10 p-8 bg-gray-100 dark:bg-gray-900 rounded-lg shadow-md">
            {/* Formularz wysyłania e-maila */}
            <div className="form-container flex flex-col w-full p-6 bg-white dark:bg-gray-800 rounded-lg shadow">
                <h2 className="text-lg font-semibold mb-4">Contact Us</h2>
                <form onSubmit={handleSubmit} className="space-y-4">
                    <div>
                        <label className="block text-sm font-medium mb-1">Your Name</label>
                        <Input
                            type="text"
                            placeholder="Enter your name"
                            name="name"
                            value={formData.name}
                            onChange={handleInputChange}
                            required
                        />
                    </div>
                    <div>
                        <label className="block text-sm font-medium mb-1">Your Email</label>
                        <Input
                            type="email"
                            placeholder="Enter your email"
                            name="email"
                            value={formData.email}
                            onChange={handleInputChange}
                            required
                        />
                    </div>
                    <div>
                        <label className="block text-sm font-medium mb-1">Subject</label>
                        <Input
                            type="text"
                            placeholder="Enter the subject"
                            name="subject"
                            value={formData.subject}
                            onChange={handleInputChange}
                            required
                        />
                    </div>
                    <div>
                        <label className="block text-sm font-medium mb-1">Message</label>
                        <Textarea
                            placeholder="Write your message here"
                            name="message"
                            value={formData.message}
                            onChange={handleInputChange}
                            required
                        />
                    </div>
                    <Button type="submit" variant="primary">
                        Send Message
                    </Button>
                </form>
                {response && <div className="mt-4 text-green-600">{response}</div>}
            </div>

            {/* FAQ Sekcja */}
            <div className="faq-container flex flex-col w-full p-6 bg-white dark:bg-gray-800 rounded-lg shadow">
                <h2 className="text-lg font-semibold mb-4">Frequently Asked Questions</h2>
                <Accordion type="single" collapsible>
                    <AccordionItem value="item-1" className="w-full">
                        <AccordionTrigger className="w-full">
                            What is the average response time?
                        </AccordionTrigger>
                        <AccordionContent>
                            Our team usually responds to inquiries within 24-48 hours. Please
                            allow extra time during weekends and holidays.
                        </AccordionContent>
                    </AccordionItem>
                    <AccordionItem value="item-2" className="w-full">
                        <AccordionTrigger className="w-full">
                            Can I update my submitted request?
                        </AccordionTrigger>
                        <AccordionContent>
                            Unfortunately, requests cannot be updated after submission.
                            However, you can cancel the previous request and submit a new one
                            if necessary.
                        </AccordionContent>
                    </AccordionItem>
                    <AccordionItem value="item-3" className="w-full">
                        <AccordionTrigger className="w-full">
                            Do you offer customer support over the phone?
                        </AccordionTrigger>
                        <AccordionContent>
                            At this time, we provide customer support via email and live chat
                            to ensure the best and most efficient communication.
                        </AccordionContent>
                    </AccordionItem>
                </Accordion>
            </div>
        </div>
    );
};

export default ContactFormAndFAQ;
