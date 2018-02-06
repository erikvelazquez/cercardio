(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('TimesDialogController', TimesDialogController);

    TimesDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Times'];

    function TimesDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Times) {
        var vm = this;

        vm.times = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.times.id !== null) {
                Times.update(vm.times, onSaveSuccess, onSaveError);
            } else {
                Times.save(vm.times, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cercardiobitiApp:timesUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
