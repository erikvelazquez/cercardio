'use strict';

describe('Controller Tests', function() {

    describe('PacientGenerals Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPacientGenerals, MockUserBD, MockGender, MockCivilStatus, MockReligion, MockEthnicGroup, MockAcademicDegree, MockSocioeconomicLevel, MockOccupation, MockLivingPlace, MockPrivateHealthInsurance, MockSocialHealthInsurance;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPacientGenerals = jasmine.createSpy('MockPacientGenerals');
            MockUserBD = jasmine.createSpy('MockUserBD');
            MockGender = jasmine.createSpy('MockGender');
            MockCivilStatus = jasmine.createSpy('MockCivilStatus');
            MockReligion = jasmine.createSpy('MockReligion');
            MockEthnicGroup = jasmine.createSpy('MockEthnicGroup');
            MockAcademicDegree = jasmine.createSpy('MockAcademicDegree');
            MockSocioeconomicLevel = jasmine.createSpy('MockSocioeconomicLevel');
            MockOccupation = jasmine.createSpy('MockOccupation');
            MockLivingPlace = jasmine.createSpy('MockLivingPlace');
            MockPrivateHealthInsurance = jasmine.createSpy('MockPrivateHealthInsurance');
            MockSocialHealthInsurance = jasmine.createSpy('MockSocialHealthInsurance');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'PacientGenerals': MockPacientGenerals,
                'UserBD': MockUserBD,
                'Gender': MockGender,
                'CivilStatus': MockCivilStatus,
                'Religion': MockReligion,
                'EthnicGroup': MockEthnicGroup,
                'AcademicDegree': MockAcademicDegree,
                'SocioeconomicLevel': MockSocioeconomicLevel,
                'Occupation': MockOccupation,
                'LivingPlace': MockLivingPlace,
                'PrivateHealthInsurance': MockPrivateHealthInsurance,
                'SocialHealthInsurance': MockSocialHealthInsurance
            };
            createController = function() {
                $injector.get('$controller')("PacientGeneralsDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'cercardiobitiApp:pacientGeneralsUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
