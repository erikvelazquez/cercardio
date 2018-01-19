(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('PacientDetailController', PacientDetailController);

    PacientDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Pacient', 'UserBD', 'Company', 'Gender', 'CivilStatus', 'Religion', 'EthnicGroup', 'AcademicDegree', 'SocioeconomicLevel', 'Occupation', 'LivingPlace', 'PrivateHealthInsurance', 'SocialHealthInsurance'];

    function PacientDetailController($scope, $rootScope, $stateParams, previousState, entity, Pacient, UserBD, Company, Gender, CivilStatus, Religion, EthnicGroup, AcademicDegree, SocioeconomicLevel, Occupation, LivingPlace, PrivateHealthInsurance, SocialHealthInsurance) {
        var vm = this;

        vm.pacient = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cercardiobitiApp:pacientUpdate', function(event, result) {
            vm.pacient = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
