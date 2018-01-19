'use strict';

describe('Controller Tests', function() {

    describe('Reservation Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockReservation, MockPacient, MockMedic, MockStatus, MockPayment, MockTimers, MockAppreciation, MockPacientMedicalAnalysis;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockReservation = jasmine.createSpy('MockReservation');
            MockPacient = jasmine.createSpy('MockPacient');
            MockMedic = jasmine.createSpy('MockMedic');
            MockStatus = jasmine.createSpy('MockStatus');
            MockPayment = jasmine.createSpy('MockPayment');
            MockTimers = jasmine.createSpy('MockTimers');
            MockAppreciation = jasmine.createSpy('MockAppreciation');
            MockPacientMedicalAnalysis = jasmine.createSpy('MockPacientMedicalAnalysis');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Reservation': MockReservation,
                'Pacient': MockPacient,
                'Medic': MockMedic,
                'Status': MockStatus,
                'Payment': MockPayment,
                'Timers': MockTimers,
                'Appreciation': MockAppreciation,
                'PacientMedicalAnalysis': MockPacientMedicalAnalysis
            };
            createController = function() {
                $injector.get('$controller')("ReservationDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'cercardiobitiApp:reservationUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
