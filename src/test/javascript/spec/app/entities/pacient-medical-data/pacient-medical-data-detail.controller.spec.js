'use strict';

describe('Controller Tests', function() {

    describe('PacientMedicalData Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPacientMedicalData, MockPacient, MockBloodGroup, MockDisabilities;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPacientMedicalData = jasmine.createSpy('MockPacientMedicalData');
            MockPacient = jasmine.createSpy('MockPacient');
            MockBloodGroup = jasmine.createSpy('MockBloodGroup');
            MockDisabilities = jasmine.createSpy('MockDisabilities');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'PacientMedicalData': MockPacientMedicalData,
                'Pacient': MockPacient,
                'BloodGroup': MockBloodGroup,
                'Disabilities': MockDisabilities
            };
            createController = function() {
                $injector.get('$controller')("PacientMedicalDataDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'cercardiobitiApp:pacientMedicalDataUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
