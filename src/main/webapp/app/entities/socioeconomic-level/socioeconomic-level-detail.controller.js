(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('SocioeconomicLevelDetailController', SocioeconomicLevelDetailController);

    SocioeconomicLevelDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SocioeconomicLevel'];

    function SocioeconomicLevelDetailController($scope, $rootScope, $stateParams, previousState, entity, SocioeconomicLevel) {
        var vm = this;

        vm.socioeconomicLevel = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cercardiobitiApp:socioeconomicLevelUpdate', function(event, result) {
            vm.socioeconomicLevel = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
