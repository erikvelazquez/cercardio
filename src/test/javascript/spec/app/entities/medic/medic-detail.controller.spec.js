'use strict';

describe('Controller Tests', function() {

    describe('Medic Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockMedic, MockUserBD, MockCompany, MockEthnicGroup, MockGender, MockCategory;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockMedic = jasmine.createSpy('MockMedic');
            MockUserBD = jasmine.createSpy('MockUserBD');
            MockCompany = jasmine.createSpy('MockCompany');
            MockEthnicGroup = jasmine.createSpy('MockEthnicGroup');
            MockGender = jasmine.createSpy('MockGender');
            MockCategory = jasmine.createSpy('MockCategory');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Medic': MockMedic,
                'UserBD': MockUserBD,
                'Company': MockCompany,
                'EthnicGroup': MockEthnicGroup,
                'Gender': MockGender,
                'Category': MockCategory
            };
            createController = function() {
                $injector.get('$controller')("MedicDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'cercardiobitiApp:medicUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
