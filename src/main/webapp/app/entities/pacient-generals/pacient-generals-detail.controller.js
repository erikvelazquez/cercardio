(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('PacientGeneralsDetailController', PacientGeneralsDetailController);

    PacientGeneralsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PacientGenerals', 'UserBD', 'Gender', 'CivilStatus', 'Religion', 'EthnicGroup', 'AcademicDegree', 'SocioeconomicLevel', 'Occupation', 'LivingPlace', 'PrivateHealthInsurance', 'SocialHealthInsurance'];

    function PacientGeneralsDetailController($scope, $rootScope, $stateParams, previousState, entity, PacientGenerals, UserBD, Gender, CivilStatus, Religion, EthnicGroup, AcademicDegree, SocioeconomicLevel, Occupation, LivingPlace, PrivateHealthInsurance, SocialHealthInsurance) {
        var vm = this;

        vm.pacientGenerals = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cercardiobitiApp:pacientGeneralsUpdate', function(event, result) {
            vm.pacientGenerals = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
