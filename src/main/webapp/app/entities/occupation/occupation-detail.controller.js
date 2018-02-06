(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('OccupationDetailController', OccupationDetailController);

    OccupationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Occupation'];

    function OccupationDetailController($scope, $rootScope, $stateParams, previousState, entity, Occupation) {
        var vm = this;

        vm.occupation = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cercardiobitiApp:occupationUpdate', function(event, result) {
            vm.occupation = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
