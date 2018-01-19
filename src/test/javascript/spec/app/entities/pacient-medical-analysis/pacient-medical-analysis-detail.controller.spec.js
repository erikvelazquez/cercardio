'use strict';

describe('Controller Tests', function() {

    describe('PacientMedicalAnalysis Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPacientMedicalAnalysis, MockMedic, MockPacient;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPacientMedicalAnalysis = jasmine.createSpy('MockPacientMedicalAnalysis');
            MockMedic = jasmine.createSpy('MockMedic');
            MockPacient = jasmine.createSpy('MockPacient');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'PacientMedicalAnalysis': MockPacientMedicalAnalysis,
                'Medic': MockMedic,
                'Pacient': MockPacient
            };
            createController = function() {
                $injector.get('$controller')("PacientMedicalAnalysisDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'cercardiobitiApp:pacientMedicalAnalysisUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
