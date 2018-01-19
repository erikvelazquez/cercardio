(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('MedicDetailController', MedicDetailController);

    MedicDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Medic', 'UserBD', 'Company', 'EthnicGroup', 'Gender', 'Category'];

    function MedicDetailController($scope, $rootScope, $stateParams, previousState, entity, Medic, UserBD, Company, EthnicGroup, Gender, Category) {
        var vm = this;

        vm.medic = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cercardiobitiApp:medicUpdate', function(event, result) {
            vm.medic = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
