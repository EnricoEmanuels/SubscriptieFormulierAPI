document.addEventListener('DOMContentLoaded', () => {
    // Functie voor validatie van formulieren
    function validateForm(fields) {
        let isValid = true;
        fields.forEach(field => {
            const input = document.getElementById(field);
            const validationMessage = document.getElementById(`${field}-validation`);
            if (!input.value.trim()) {
                validationMessage.style.display = 'block';
                isValid = false;
            } else {
                validationMessage.style.display = 'none';
            }
        });
        return isValid;
    }


    // // CREATE (POST) - Nieuw abonnement maken
    // document.getElementById('createSubscriptionForm').addEventListener('submit', async (event) => {
    //     event.preventDefault();
    //     if (!validateForm(['firstname', 'lastname', 'email'])) return;
    //     const formData = new FormData(event.target);
    //     const data = Object.fromEntries(formData.entries());
    //     try {
    //         const response = await fetch('http://localhost:8080/api/subscriptions/create', {
    //             method: 'POST',
    //             headers: { 'Content-Type': 'application/json' },
    //             body: JSON.stringify(data)
    //         });
    //         const result = await response.json();
    //         document.getElementById('createResponse').textContent = JSON.stringify(result, null, 2);
    //     } catch (error) {
    //         console.error('Error creating subscription:', error);
    //     }
    // });

    document.getElementById('createSubscriptionForm').addEventListener('submit', async (event) => {
        event.preventDefault();

        if (!validateForm(['firstname', 'lastname', 'email'])) return;

        const formData = new FormData(event.target);
        const data = Object.fromEntries(formData.entries());

        try {
            const response = await fetch('http://localhost:8080/api/subscriptions/create', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(data)
            });

            const result = await response.json();
            const responseDiv = document.getElementById('createResponse');

            responseDiv.innerHTML = `
            Subscriptie succesvol aangemaakt:<br>
            <b>ID:</b> ${result.id}<br>
            <b>Voornaam:</b> ${result.firstname}<br>
            <b>Achternaam:</b> ${result.lastname}<br>
            <b>Email:</b> ${result.email}<br>
            <b>Telefoonnummer:</b> ${result.phonenumber || 'N.V.T.'}
        `;

        } catch (error) {
            console.error('Fout bij het aanmaken van subscriptie:', error);
            document.getElementById('createResponse').textContent = "Fout bij het aanmaken van subscriptie.";
        }
    });



    // UPDATE (PUT) - Abonnement updaten
    // document.getElementById('updateSubscriptionForm').addEventListener('submit', async (event) => {
    //     event.preventDefault();
    //     const id = document.getElementById('update-id').value.trim();
    //     const firstname = document.getElementById('update-firstname').value.trim();
    //     const lastname = document.getElementById('update-lastname').value.trim();
    //     const email = document.getElementById('update-email').value.trim();
    //     const phonenumber = document.getElementById('update-phonenumber').value.trim();
    //     if (!id) {
    //         alert("Subscription ID is required for update!");
    //         return;
    //     }
    //     // Verzamelen van alle velden om door te sturen
    //     const updatedSubscription = {
    //         firstname: firstname,
    //         lastname: lastname,
    //         email: email,
    //         phonenumber: phonenumber
    //     };
    //     try {
    //         const response = await fetch(`http://localhost:8080/api/subscriptions/${id}`, {
    //             method: 'PUT',
    //             headers: { 'Content-Type': 'application/json' },
    //             body: JSON.stringify(updatedSubscription)
    //         });
    //         const result = await response.json();
    //         document.getElementById('updateResponse').textContent = JSON.stringify(result, null, 2);
    //     } catch (error) {
    //         console.error('Error updating subscription:', error);
    //     }
    // });

    document.getElementById('updateSubscriptionForm').addEventListener('submit', async (event) => {
        event.preventDefault();

        const id = document.getElementById('update-id').value.trim();
        const firstname = document.getElementById('update-firstname').value.trim();
        const lastname = document.getElementById('update-lastname').value.trim();
        const email = document.getElementById('update-email').value.trim();
        const phonenumber = document.getElementById('update-phonenumber').value.trim();

        if (!id) {
            alert("Subscription ID is required for update!");
            return;
        }

        const updatedSubscription = {
            firstname,
            lastname,
            email,
            phonenumber
        };

        try {
            const response = await fetch(`http://localhost:8080/api/subscriptions/${id}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(updatedSubscription)
            });

            const result = await response.json();
            const responseDiv = document.getElementById('updateResponse');

            responseDiv.innerHTML = `
            Subscriptie succesvol bijgewerkt:<br>
            <b>ID:</b> ${id}<br>
            <b>Voornaam:</b> ${result.firstname}<br>
            <b>Achternaam:</b> ${result.lastname}<br>
            <b>Email:</b> ${result.email}<br>
            <b>Telefoonnummer:</b> ${result.phonenumber || 'N.V.T.'}
        `;
        } catch (error) {
            console.error('Error updating subscription:', error);
            document.getElementById('updateResponse').textContent = "Fout bij het bijwerken van subscriptie.";
        }
    });


    // DELETE - Abonnement verwijderen
    document.getElementById('deleteSubscriptionForm').addEventListener('submit', async (event) => {
        event.preventDefault();
        const id = document.getElementById('delete-id').value.trim();
        if (!id) {
            alert("Subscription ID is required for deletion!");
            return;
        }
        try {
            const response = await fetch(`http://localhost:8080/api/subscriptions/${id}`, {
                method: 'DELETE'
            });
            document.getElementById('deleteResponse').textContent = response.ok
                ? `Subscription ID: ${id} successfully deleted.`
                : `Failed to delete subscription with ID:  ${id}.`;
        } catch (error) {
            console.error('Error deleting subscription:', error);
        }
    });
    // // FETCH ALL SUBSCRIPTIONS
    // document.getElementById('fetchSubscriptionsButton').addEventListener('click', async () => {
    //     try {
    //         const response = await fetch('http://localhost:8080/api/subscriptions');
    //         const subscriptions = await response.json();
    //         document.getElementById('subscriptions').textContent = JSON.stringify(subscriptions, null, 2);
    //     } catch (error) {
    //         console.error('Error fetching subscriptions:', error);
    //     }
    // });


    // FETCH ALL SUBSCRIPTIONS - ALS NETTE RECORDS
    document.getElementById('fetchSubscriptionsButton').addEventListener('click', async () => {
        try {
            const response = await fetch('http://localhost:8080/api/subscriptions');
            const subscriptions = await response.json();
            const subscriptionsDiv = document.getElementById('subscriptions');

            if (subscriptions.length === 0) {
                subscriptionsDiv.innerHTML = "Geen subscripties gevonden.";
                return;
            }

            let output = '';
            subscriptions.forEach(sub => {
                output += `
                <div style="margin-left: -150px;" >
                    <strong>ID:</strong> ${sub.id}<br>
                    <strong>Voornaam:</strong> ${sub.firstname}<br>
                    <strong>Achternaam:</strong> ${sub.lastname}<br>
                    <strong>Email:</strong> ${sub.email}<br>
                    <strong>Telefoonnummer:</strong> ${sub.phonenumber}<br>
                </div>
            `;
            });

            subscriptionsDiv.innerHTML = output;

        } catch (error) {
            console.error('Error fetching subscriptions:', error);
            document.getElementById('subscriptions').textContent = "Fout bij het ophalen van subscripties.";
        }
    });



    // //fetch one subscription
    // async function getSubscriptionsById(id) {
    //     // stap 1: checken als het id voorkomt
    //     if (id == null){
    //         console.error(`Id ${id} does not exist`);
    //         return;
    //     }
    //
    //     try {
    //         const response = await fetch(`http://localhost:8080/api/subscriptions/${id}`);
    //         // stap 2 check de HTTP Status
    //         if (!response.ok){
    //             console.error(`HTTP error! Status: ${response.status}, Status Text: ${response.statusText}`);
    //             // console.error(`HTTP error! Status: ${response.status}, Status Text: ${response.message}`);
    //             if (response.status === 404) {
    //                 console.log(`ID: ${id} not found`)
    //             }
    //             return;
    //         }
    //         const subscriptions = await response.json();
    //         const subscriptionsDiv = document.getElementById('subscriptions');
    //         subscriptionsDiv.innerHTML = JSON.stringify(subscriptions, null, 2);
    //     } catch (error) {
    //         console.error('Error fetching subscriptions:', error);
    //     }
    // }
    //
    // getSubscriptionsById(169);

    // // FETCH ONE SUBSCRIPTION - OP BASIS VAN ID
    // document.getElementById('getOneSubscriptionForm').addEventListener('submit', async (event) => {
    //     event.preventDefault();
    //     const id = document.getElementById('get-id').value.trim();
    //     const responseBox = document.getElementById('getOneResponse');
    //
    //     if (!id) {
    //         responseBox.textContent = "Please enter a subscription ID.";
    //         return;
    //     }
    //
    //     try {
    //         const response = await fetch('http://localhost:8080/api/subscriptions/${id}');
    //         if (!response.ok) {
    //             if (response.status === 404) {
    //                 responseBox.textContent = Subscription with ID ${id} not found.;
    //             } else {
    //                 responseBox.textContent = Error: ${response.status} - ${response.statusText};
    //             }
    //             return;
    //         }
    //
    //         const subscription = await response.json();
    //         responseBox.textContent = JSON.stringify(subscription, null, 2);
    //     } catch (error) {
    //         console.error('Error fetching subscription by ID:', error);
    //         responseBox.textContent = "An error occurred while fetching the subscription.";
    //     }
    // });

    document.getElementById('getOneSubscriptionForm').addEventListener('submit', async (event) => {
        event.preventDefault();

        const id = document.getElementById('get-id').value.trim();
        const responseField = document.getElementById('getOneResponse');

        if (!id) {
            responseField.textContent = "Voer een subscriptie-ID in.";
            return;
        }

        try {
            const response = await fetch(`http://localhost:8080/api/subscriptions/${id}`);

            if (!response.ok) {
                if (response.status === 404) {
                    responseField.textContent = `Subscriptie met ID ${id} niet gevonden.`;
                } else {
                    responseField.textContent = `Fout: ${response.status} - ${response.statusText}`;
                }
                return;
            }

            const subscription = await response.json();
            responseField.innerHTML = `
            Subscriptie gevonden:<br>
            <b>ID:</b> ${subscription.id}<br>
            <b>Voornaam:</b> ${subscription.firstname}<br>
            <b>Achternaam:</b> ${subscription.lastname}<br>
            <b>Email:</b> ${subscription.email}<br>
            <b>Telefoonnummer:</b> ${subscription.phonenumber}`;
        } catch (error) {
            console.error('Fout bij het ophalen van subscriptie:', error);
            responseField.textContent = "Er is een fout opgetreden bij het ophalen van de subscriptie.";
        }
    });
            // ``


});

